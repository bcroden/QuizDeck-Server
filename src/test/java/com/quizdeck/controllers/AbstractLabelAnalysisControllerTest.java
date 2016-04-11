package com.quizdeck.controllers;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.MockMember;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Selection;
import com.quizdeck.model.database.*;
import com.quizdeck.model.inputs.OwnerLabelsInput;
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

import java.util.*;

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
public abstract class AbstractLabelAnalysisControllerTest {

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Before
    public void seedCompletedQuizRepository() {
        LABELS.add("Label1");
        LABELS.add("Label2");

        for(int i = 0; i < QUIZ_TITLE.length; i++)
            completeQuizzes.add(getCompleteQuiz(QUIZ_TITLE[i], NUM_QUESTIONS_IN_QUIZ[i]));

        completedQuizRepository.save(completeQuizzes);
        input.setOwner(completeQuizzes.get(0).getOwner());
        input.setLabels(LABELS);
    }

    @After
    public void cleanCompletedQuizRepository() {
        completedQuizRepository.delete(completeQuizzes);
    }

    private static CompleteQuiz getCompleteQuiz(String title, int numQuestions) {
        List<Questions> questions = getNQuestions(numQuestions);
        List<String> categories = Collections.singletonList("Category1");
        Quiz quiz = new Quiz("owner", title, questions, LABELS, categories, true);
        List<submission> submissions = getSubmissionsForQuiz(quiz);
        return new CompleteQuiz(quiz, new Date(), new Date(), title, "owner", submissions);
    }

    private static List<Questions> getNQuestions(final int N) {
        List<Questions> questions = new LinkedList<>();
        for(int i = 0; i < N; i++) {
            Questions question = new Questions();
            question.setAnswers(getAnswers());
            question.setQuestionNum(i+1);
            question.setQuestion("Q" + (i+1));
            question.setCorrectAnswerID(question.getAnswers().get(0).getId());
            question.setQuestionFormat("QuestionFormat");
            questions.add(question);
        }

        return questions;
    }
    private static List<Answers> getAnswers() {
        Answers answer1 = new Answers();
        answer1.setId("1");
        answer1.setContent("A1");
        Answers answer2 = new Answers();
        answer2.setId("2");
        answer2.setContent("A2");
        List<Answers> answers = new LinkedList<>();
        answers.add(answer1);
        answers.add(answer2);
        return answers;
    }

    private static List<submission> getSubmissionsForQuiz(Quiz quiz) {
        List<submission> submissions = new LinkedList<>();

        for(Questions question : quiz.getQuestions()) {
            submission bilboSub = new submission();
            bilboSub.setQuestion(question);
            bilboSub.setGuesses(getCorrectGuessesFor(question));
            bilboSub.setUserName(BILBO.getUsername());
            submissions.add(bilboSub);

            submission gimliSub = new submission();
            gimliSub.setQuestion(question);
            gimliSub.setGuesses(getIncorrectGuessFor(question));
            gimliSub.setUserName(GIMLI.getUsername());
            submissions.add(gimliSub);
        }

        return submissions;
    }
    private static List<Guess> getCorrectGuessesFor(Questions question) {
        List<Guess> guesses = new LinkedList<>();

        guesses.add(new Guess(question.getAnswers().get(0), System.currentTimeMillis(), question.getQuestionNum()));
        guesses.add(new Guess(question.getCorrectAnswer(), System.currentTimeMillis()+1000, question.getQuestionNum()));

        return guesses;
    }
    private static List<Guess> getIncorrectGuessFor(Questions question) {
        List<Guess> guesses = new LinkedList<>();

        Selection selection = question.getAnswers().stream().filter(answer -> !question.getCorrectAnswer().equals(answer)).findFirst().get();
        guesses.add(new Guess(selection, System.currentTimeMillis(), question.getQuestionNum()));

        return guesses;
    }

    protected List<String> getLabels() {
        return LABELS;
    }
    protected OwnerLabelsInput getInput() {
        return input;
    }

    private static final OwnerLabelsInput input = new OwnerLabelsInput();
    private static final List<CompleteQuiz> completeQuizzes = new LinkedList<>();
    private static final String[] QUIZ_TITLE = new String[]{"Quiz1Title", "Quiz2Title", "Quiz3Title"};
    private static final int[] NUM_QUESTIONS_IN_QUIZ = {3, 3, 3};
    private static final List<String> LABELS = new LinkedList<>();
    private static final Member BILBO = new MockMember("Bilbo"),
                                GIMLI = new MockMember("Gimli");

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