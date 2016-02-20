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
 * @author Alex
 */
public class QuizAccuracyAlgorithmTest {
    /**
     * Use mock objects to simulate quiz results
     */
    @Before
    public void initializationTest() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();

        factory.setOwnerID(OWNER);
        factory.setDeckID(DECK_ID);
        factory.setQuizID(QUIZ_ID);

        List<Question> questions = new LinkedList<>();
        for(int i = 1; i < 6; i++)
            questions.add(new MockQuestion(i, new MockSelection(Integer.toString(i).charAt(0))));
        factory.setQuestions(questions);

        List<Response> responses = new LinkedList<>();

        //Mr. Howell guessed the 5th option to the first question at t = 1 and the 1st at t = 2
        responses.add(new MockResponse(MR_HOWELL, new MockSelection('5'), questions.get(0), 1));
        responses.add(new MockResponse(MR_HOWELL, new MockSelection('1'), questions.get(0), 2));

        //Gilligan guessed the question number for each question at t = 2
        for(int i = 1; i < 6; i++)
            responses.add(new MockResponse(GILLIGAN, new MockSelection(Integer.toString(i).charAt(0)), questions.get(i-1), 2));
        factory.setResponses(responses);

        analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
    }

    /**
     * Ensure that the analysis throws the correct exception with necessary
     */
    @Test(expected = AnalysisResultsUnavailableException.class)
    public void resultsNotReady() throws AnalysisResultsUnavailableException {
        analysis.getResults();
    }

    /**
     * Test statistics calculated at the quiz level
     */
    @Test
    public void testQuizLevelCalculations() throws AnalysisResultsUnavailableException {
        analysis.performAnalysis();
        QuizAnalysisData results = (QuizAnalysisData) analysis.getResults();

        assertThat("Incorrect number of participants", results.getData().keySet().size(), is(2));
        assertEquals("Incorrect average accuracy per participant",
                0.6,
                Double.parseDouble(results.getStats().get("Average Accuracy Per Participant")),
                0.0001);
    }

    /**
     * Test analysis of participant who submitted a guess to all questions.
     */
    @Test
    public void testFullyEngagedParticipant() throws AnalysisResultsUnavailableException {
        //Perform analysis and acquire results
        analysis.performAnalysis();
        QuizAnalysisData quizData = (QuizAnalysisData) analysis.getResults();
        QuizParticipantAnalysisData gilliganData = quizData.getData().get(GILLIGAN.getUsername());

        //Test accuracy
        assertEquals(   "Accuracy grade should be 100%",
                        1.0,
                        Double.parseDouble(gilliganData.getStats().get("Accuracy Percentage")),
                        0.0001);

        for(Question question : gilliganData.getData().keySet()) {
            //only one selection should be in the data
            assertThat("Only one response per question", gilliganData.getData().get(question).size(), is(1));

            //Ensure that the grade of 100% is deserved
            assertThat("Incorrect final submission for " + GILLIGAN.getUsername() + " on #" + question.getQuestionNum(),
                    gilliganData.getData().get(question).get(0).getSelection(),
                    is(quizData.getAnswerKey().get(question)));
        }
    }

    /**
     * Test analysis of participant who did not submit a guess to all of the questions.
     */
    @Test
    public void testPartiallyEngagedParticipant() throws AnalysisResultsUnavailableException {
        //test analysis of participant who did not submit a response for all questions
        analysis.performAnalysis();
        QuizAnalysisData quizData = (QuizAnalysisData) analysis.getResults();
        QuizParticipantAnalysisData howellData = quizData.getData().get(MR_HOWELL.getUsername());
        assertEquals(   "Accuracy should be 20%",
                        0.2,
                        Double.parseDouble(howellData.getStats().get("Accuracy Percentage")),
                        0.0001);

        for(Question question : howellData.getData().keySet()) {
            //only one selection should be in the data
            assertThat("More than one response by " + MR_HOWELL.getUsername() + " on # " + question.getQuestionNum(),
                    howellData.getData().get(question).size(),
                    is(1));

            //Ensure that the grade of 100% is deserved
            assertThat("Incorrect final submission for " + MR_HOWELL.getUsername() + " on #" + question.getQuestionNum(),
                    howellData.getData().get(question).get(0).getSelection(),
                    is(quizData.getAnswerKey().get(question)));
        }
    }

    /**
     * Make sure that the owner, quiz ID, and deck ID are preserved during the analysis.
     */
    @Test
    public void testAnalysisID() throws AnalysisResultsUnavailableException {
        analysis.performAnalysis();
        QuizAnalysisData quizResults = (QuizAnalysisData) analysis.getResults();
        assertThat("Incorrect quiz owner", quizResults.getOwnerID(), is(OWNER));
        assertThat("Incorrect quiz ID", quizResults.getQuizID(), is(QUIZ_ID));
        assertThat("Incorrect deck ID", quizResults.getDeckID(), is(DECK_ID));
    }

    private StaticAnalysis analysis;
    private final Member GILLIGAN = new MockMember("Gilligan");
    private final Member MR_HOWELL = new MockMember("Mr. Howell");
    private final Member PROFESSOR = new MockMember("Professor");
    private final String OWNER = PROFESSOR.getUsername();
    private final String QUIZ_ID = "123", DECK_ID = "ABC";
}
