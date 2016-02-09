package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.*;
import com.quizdeck.analysis.QuizAlgorithm;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import com.quizdeck.analysis.outputs.QuizParticipantAnalysisData;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * Test for the Quiz Accuracy Algorithm
 *
 * Note: com.quizdeck.analysis.QuizAlgorithm must be explicitly imported. Right now it
 *          is not being brought in by the wildcard, so I am importing it explicitly
 *          for now.
 *
 * //TODO: Test for clearing of values when after creating a new analysis
 * @author Alex
 */
public class QuizAccuracyAlgorithmTest {
    @Before
    public void initializationTest() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();

        factory.setOwner(new MockMember("The Professor"));
        factory.setDeckID("DeckID");
        factory.setQuizID("QuizID");

        List<Question> questions = new LinkedList<>();
        for(int i = 1; i < 6; i++)
            questions.add(new MockQuestion(i, new MockSelection(Integer.toString(i).charAt(0))));
        factory.setQuestions(questions);

        List<Response> responses = new LinkedList<>();
        Member mrHowell = new MockMember("Mr. Howell");
        //Mr. Howell guessed the 5th option to the first question at t = 1
        responses.add(new MockResponse(mrHowell, new MockSelection('5'), questions.get(0), 1));

        //Gilligan guessed the question number for each question at t = 2
        Member gilligan = GILLIGAN;
        for(int i = 1; i < 6; i++)
            responses.add(new MockResponse(gilligan, new MockSelection(Integer.toString(i).charAt(0)), questions.get(i-1), 2));
        factory.setResponses(responses);

        analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
    }

    @Test(expected = AnalysisResultsUnavailableException.class)
    public void resultsNotReady() throws AnalysisResultsUnavailableException {
        analysis.getResults();
    }

    @Test
    public void testQuizLevelCalculations() throws AnalysisResultsUnavailableException {
        analysis.performAnalysis();
        QuizAnalysisData results = (QuizAnalysisData) analysis.getResults();

        assertThat("Incorrect number of participants", results.getData().keySet().size(), is(2));
    }

    @Test
    public void testFullyEngagedParticipant() throws AnalysisResultsUnavailableException {
        //test analysis of participant who engaged in all questions
        analysis.performAnalysis();
        QuizAnalysisData results = (QuizAnalysisData) analysis.getResults();
        QuizParticipantAnalysisData data = results.getData().get(GILLIGAN);
        assertEquals(   "Accuracy grade should be 100",
                        1.0,
                        Double.parseDouble(data.getStats().get("Accuracy Percentage")),
                        0.0001);
    }

    private StaticAnalysis analysis;
    private final Member GILLIGAN = new MockMember("Gilligan");
}
