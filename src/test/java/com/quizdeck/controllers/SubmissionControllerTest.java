package com.quizdeck.controllers;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.model.database.*;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
import com.quizdeck.services.RedisSubmissions;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by Cade on 4/28/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class SubmissionControllerTest {

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Resource(name = "secretKey")
    private String secretKey;

    @Resource
    private WebApplicationContext webApplicationContext;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @Autowired
    RedisSubmissions redisSubmissions;

    @Autowired
    RedisQuestion redisQuestion;

    @Resource
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSubmit() throws Exception{

        List<Questions> questions = new ArrayList<>();
        List<Answers> answers = new ArrayList<>();
        for(int i = 1; i < 5; i++){
            Answers answer = new Answers("this", i+"");
        }
        Questions question1 = new Questions();
        question1.setQuestion("What?");
        question1.setQuestionNum(0);
        question1.setAnswers(answers);
        question1.setCorrectAnswerID("A");
        Questions questions2 = new Questions();
        questions2.setQuestion("How?");
        questions2.setQuestionNum(1);
        questions2.setAnswers(answers);
        questions2.setCorrectAnswerID("b");
        questions.add(question1);
        questions.add(questions2);

        Quiz quiz = new Quiz();
        quiz.setOwner("user2");
        quiz.setQuestions(questions);
        quiz.setTitle("Test Quiz");
        quiz.setId("abc1234");
        ArrayList<String> cats = new ArrayList<>();
        cats.add("beans");
        quiz.setCategories(cats);

        ActiveQuiz activeQuiz = new ActiveQuiz(new Date(), true);

        redisActiveQuiz.addEntry(quiz.getId(), activeQuiz);
        redisQuestion.addEntry(quiz.getId(), 0);

        assertThat(redisQuestion.getEntry(quiz.getId()), is(equalTo(0)));

        SubmissionInput sub = new SubmissionInput();
        sub.setUserName("user");
        Guess guess = new Guess();
        guess.setTimeStamp(System.currentTimeMillis());
        sub.setChosenAnswer("A");
        sub.setChosenAnswerContent("this");
        sub.setQuestionNum(1);
        sub.setQuizID(quiz.getId());

        mockMvc.perform(post("/rest/secure/quiz/submission")
                .content(this.json(sub))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());


        assertThat((redisSubmissions.getAllSubmissions(quiz.getId()).get(0)).getUserName(), is(equalTo("user")));


        quizRepository.removeById(quiz.getId());
        redisSubmissions.getAllSubmissionsAndRemove(quiz.getId());
        redisActiveQuiz.removeEntry(quiz.getId());
        redisQuestion.removeEntry(quiz.getId());
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }


}
