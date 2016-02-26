package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.*;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * Created by Alex on 2/25/2016.
 */
public class QuizIndecisivenessAlgorithmTest {
    /**
     * Use mock objects to simuate quiz results
     */
    @Before
    public void setup() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setOwnerID(OWNER);
        factory.setDeckID(DECK_ID);
        factory.setQuizID(QUIZ_ID);

        List<Question> questions = IntStream.range(1, 4).mapToObj(i ->
                                        new MockQuestion(i, new MockSelection(Integer.toString(i).charAt(0)))
                                    )
                                    .collect(Collectors.toList());
        factory.setQuestions(questions);

        List<Response> responses = new LinkedList<>();

        //Ginger got lucky and guessed all of them correctly the first time
        questions.forEach(question -> responses.add(new MockResponse(GINGER, new MockSelection(Integer.toString(question.getQuestionNum()).charAt(0)), question, 1)));

        //Mary Ann pushed submitted twice just to make sure
        questions.forEach(question -> responses.add(new MockResponse(MARY_ANN, new MockSelection(Integer.toString(question.getQuestionNum()).charAt(0)), question, 1)));
        questions.forEach(question -> responses.add(new MockResponse(MARY_ANN, new MockSelection(Integer.toString(question.getQuestionNum()).charAt(0)), question, 2)));

        factory.setResponses(responses);

        Analysis analysis = factory.getAnalysisUsing(QuizAlgorithm.INDECISIVENESS);
        analysis.performAnalysis();
        data = (QuizAnalysisData) analysis.getResults();
    }

    @Test
    public void blankTest() {
        assertThat("Data should not be null", data, notNullValue());
    }

    private QuizAnalysisData data;
    private final Member MARY_ANN = new MockMember("Mary Ann"),
                            GINGER = new MockMember("Ginger"),
                            MRS_HOWELL = new MockMember("Mrs. Howell");
    private final String OWNER = MRS_HOWELL.getUsername(),
                            QUIZ_ID = "123",
                            DECK_ID = "ABC";
}
