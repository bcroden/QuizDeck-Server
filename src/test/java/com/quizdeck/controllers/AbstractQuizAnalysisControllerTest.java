package com.quizdeck.controllers;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.model.database.*;
import com.quizdeck.model.inputs.AccuracyInput;
import com.quizdeck.repositories.CompletedQuizRepository;
import org.junit.After;
import org.junit.Before;
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

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Test the analysis controller
 *
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public abstract class AbstractQuizAnalysisControllerTest {

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void seedCompletedQuizRepository() {
        Quiz quiz = new Quiz("Owner ID", "Quiz Title", getQuestions(), new LinkedList<>());

        completeQuiz = new CompleteQuiz(quiz, new Date(), new Date(), quiz.getTitle(), quiz.getOwner(), getSubmissionsFor(quiz));

        completedQuizRepository.save(completeQuiz);

        //create the input object which will be passed to the controller
        accuracyInput = new AccuracyInput();
        accuracyInput.setOwner(completeQuiz.getOwner());
        accuracyInput.setTitle(completeQuiz.getTitle());
        accuracyInput.setId(completeQuiz.getQuizId());
    }

    @After
    public void cleanCompletedQuizRepository() {
        completedQuizRepository.removeById(completeQuiz.getQuizId());
    }


    private List<submission> getSubmissionsFor(Quiz quiz) {
        List<submission> submissions = new LinkedList<>();

        for(Questions question : quiz.getQuestions()) {
            submission sub = new submission();
            sub.setUserName("User #1");
            sub.setChoosenAnswers(getGuessesTo(question));
            sub.setQuestion(question);
            submissions.add(sub);
        }

        return submissions;
    }

    private List<Guess> getGuessesTo(Questions question) {
        return IntStream.range(1, 6).mapToObj(i -> new Guess(question.getAnswers().get(i % question.getAnswers().size()),
                                                                System.currentTimeMillis(),
                                                                question.getQuestionNum()))
                                    .collect(Collectors.toList());
    }

    private List<Questions> getQuestions() {
        List<Questions> questions = new LinkedList<>();

        for(int i = 0; i < 10; i++) {
            Questions q = new Questions();
            q.setQuestionNum(i+1);
            q.setQuestionFormat("Format for question #" + (i+1));
            q.setQuestion("What is in my pocket?");
            q.setAnswers(getAnswers());
            q.setCorrectAnswerID(q.getAnswers().get(i % q.getAnswers().size()).getId());
            questions.add(q);
        }

        return questions;
    }

    private List<Answers> getAnswers() {
        List<Answers> answers = new LinkedList<>();

        for(int i = 0; i < 4; i++) {
            Answers a = new Answers();
            a.setId(Integer.toString(i+1));
            a.setContent("Answer #" + (i+1));
            answers.add(a);
        }

        return answers;
    }

    protected CompleteQuiz completeQuiz;
    protected AccuracyInput accuracyInput;

    /*** Mocking out the controller ***/

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private CompletedQuizRepository completedQuizRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .get();
        assertThat("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter, is(notNullValue()));
    }

    protected String json(Object o) throws Exception {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    protected MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
}