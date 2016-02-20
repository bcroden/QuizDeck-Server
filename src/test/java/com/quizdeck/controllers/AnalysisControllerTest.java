package com.quizdeck.controllers;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.model.database.*;
import com.quizdeck.repositories.CompletedQuizRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Test the analysis controller
 *
 * @author Alex
 */
public class AnalysisControllerTest {

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void seedCompletedQuizRepository() {
        Quiz quiz = new Quiz();
        quiz.setId("Quiz Object ID");
        quiz.setQuizId("Quiz ID");
        quiz.setTitle("Quiz Title");
        quiz.setQuestions(getQuestions());

        completeQuiz.setId("Complete Quiz Object ID");
        completeQuiz.setOwner("Owner ID");
        completeQuiz.setQuiz(quiz);
        completeQuiz.setStart(new Date());
        completeQuiz.setStop(new Date());
        completeQuiz.setTitle("Complete Quiz Title");
        completeQuiz.setSubmissions(getSubmissionsFor(quiz));

        //TODO: Seed the repository with a completed quiz
    }

    public void testQuizAccuracyResults() {
        //TODO: Verify the results of the quiz accuracy algorithm on the seeded data
    }

    private List<submission> getSubmissionsFor(Quiz quiz) {
        List<submission> submissions = new LinkedList<>();
        for(int i = 0; i < 5; i++) {
            for(Questions question : quiz.getQuestions()) {
                submission sub = new submission();
                sub.setUserName("User #" + (i + 1));
                sub.setChoosenAnswers(getGuessesTo(question));
                sub.setQuestion(question);
                submissions.add(sub);
            }
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

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
}
