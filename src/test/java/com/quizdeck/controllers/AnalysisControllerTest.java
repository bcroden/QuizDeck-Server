package com.quizdeck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.jayway.jsonpath.JsonPath;
import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import com.quizdeck.analysis.outputs.QuizParticipantAnalysisData;
import com.quizdeck.model.database.*;
import com.quizdeck.model.inputs.AccuracyInput;
import com.quizdeck.repositories.CompletedQuizRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.After;
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

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test the analysis controller
 *
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class AnalysisControllerTest {

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void seedCompletedQuizRepository() {
        Quiz quiz = new Quiz();
        quiz.setId("Quiz DB ID");
        quiz.setQuizId("Quiz ID");
        quiz.setTitle("Quiz Title");
        quiz.setQuestions(getQuestions());

        completeQuiz = new CompleteQuiz();
        completeQuiz.setId("Complete Quiz DB ID");
        completeQuiz.setOwner("Owner ID");
        completeQuiz.setQuiz(quiz);
        completeQuiz.setStart(new Date());
        completeQuiz.setStop(new Date());
        completeQuiz.setTitle("Complete Quiz Title");
        completeQuiz.setSubmissions(getSubmissionsFor(quiz));

        completedQuizRepository.save(completeQuiz);

        //create the input object which will be passed to the controller
        accuracyInput = new AccuracyInput();
        accuracyInput.setOwner(completeQuiz.getOwner());
        accuracyInput.setTitle(completeQuiz.getTitle());
    }

    @After
    public void cleanCompletedQuizRepository() {
        completedQuizRepository.removeById(completeQuiz.getId());
    }

    @Test
    public void testQuizAccuracyResults() throws Exception {
        String result = mockMvc.perform(post("/rest/anaylsis/accuracy/").content(this.json(accuracyInput))
                                                .contentType(MediaType.APPLICATION_JSON)
                                        ).andExpect(status().is2xxSuccessful())
                                        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                                        .andReturn()
                                        .getResponse()
                                        .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        QuizAnalysisData data = mapper.readValue(result, QuizAnalysisData.class);

        checkAnswerKey(data.getQuestions());
        checkQuizInfo(data);
        checkQuizStats(data);
    }

    private void checkAnswerKey(List<Question> questions) {
        assertThat(questions.toString(), questions.size(), is(completeQuiz.getQuiz().getQuestions().size()));

        int numHits = 0;
        for(Question question : questions) {
            if(completeQuiz.getQuiz().getQuestions().contains(question))
                numHits++;
        }

        assertThat("Incorrect questions in answer key", numHits, is(completeQuiz.getQuiz().getQuestions().size()));
    }

    private void checkQuizInfo(QuizAnalysisData data){
        assertThat("Bad quiz owner ID", data.getOwnerID(), is(completeQuiz.getOwner()));
        assertThat("Bad quiz ID", data.getQuizID(), is(completeQuiz.getQuiz().getQuizId()));
    }

    private void checkQuizStats(QuizAnalysisData data) {
        assertThat("Bad \'Average Accuracy Per Participant\'", data.getStats().get("Average Accuracy Per Participant"), is("0.3"));
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

    private CompleteQuiz completeQuiz;
    private AccuracyInput accuracyInput;

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

    private String json(Object o) throws Exception {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
}