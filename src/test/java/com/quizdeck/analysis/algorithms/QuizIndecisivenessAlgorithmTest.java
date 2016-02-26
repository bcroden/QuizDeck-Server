package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.*;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import com.quizdeck.analysis.outputs.QuizParticipantAnalysisData;
import com.quizdeck.model.database.Quiz;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * Created by Alex on 2/25/2016.
 */
public class QuizIndecisivenessAlgorithmTest {
    /**
     * Use mock objects to simuate quiz results
     */
    @BeforeClass
    public static void setup() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setOwnerID(OWNER_ID);
        factory.setDeckID(DECK_ID);
        factory.setQuizID(QUIZ_ID);

        questions = IntStream.range(1, 4).mapToObj(i ->
                                        new MockQuestion(i, new MockSelection(Integer.toString(i).charAt(0)))
                                    )
                                    .collect(Collectors.toList());
        factory.setQuestions(questions);

        List<Response> responses = new LinkedList<>();

        //Ginger got lucky and guessed all of them correctly the first time
        questions.forEach(question -> responses.add(new MockResponse(GINGER, new MockSelection('1'), question, 1)));

        //Mary Ann submit once for the 1st question, twice for the 2nd, etc.
        for(int i = 0; i < questions.size(); i++)
            for(int j = 0; j <= i; j++)
                responses.add(new MockResponse(MARY_ANN, new MockSelection('1'), questions.get(i), j+2));

        factory.setResponses(responses);

        Analysis analysis = factory.getAnalysisUsing(QuizAlgorithm.INDECISIVENESS);
        analysis.performAnalysis();
        quizAnalysisData = (QuizAnalysisData) analysis.getResults();
    }

    @Test
    public void testMetaData() {
        assertThat("Data should not be null", quizAnalysisData, notNullValue());
        assertThat("Incorrect owner ID", quizAnalysisData.getOwnerID(), is(OWNER_ID));
        assertThat("Incorrect deck ID", quizAnalysisData.getDeckID(), is(DECK_ID));
        assertThat("Incorrect quiz ID", quizAnalysisData.getQuizID(), is(QUIZ_ID));
    }

    @Test
    public void testAnswerKey() {
        assertThat("Incorrect answer key", quizAnalysisData.getQuestions(), is(questions));
    }

    @Test
    public void testNumGuessesPerParticipant() {
        QuizParticipantAnalysisData gingerData = quizAnalysisData.getData().get(GINGER.getUsername());
        gingerData.getData().values().forEach(guesses -> assertThat("Ginger only submitted one guess per question", guesses.size(), is(1)));

        QuizParticipantAnalysisData maryAnnData = quizAnalysisData.getData().get(MARY_ANN.getUsername());
        maryAnnData.getData().keySet().forEach(questionNum ->
                assertThat("The number of Mary Ann's submissions is equal to the question number",
                            maryAnnData.getData().get(questionNum).size(),
                            is(questionNum))
        );
    }

    @Test
    public void testParticipantIndecisivenessPerQuestion() {
        QuizParticipantAnalysisData gingerData = quizAnalysisData.getData().get(GINGER.getUsername());
        questions.stream().forEach(question -> {
            String stat = gingerData.getStats().get("Indecisiveness Score for Q" + question.getQuestionNum());
            assertThat("Missing participant indecisiveness score for question #" + question.getQuestionNum(),
                        stat,
                        notNullValue());
            int score = Integer.parseInt(stat);
            assertThat("Incorrect participant indecisiveness score for question #" + question.getQuestionNum(),
                        score,
                        is(0));
        });

        QuizParticipantAnalysisData maryAnnData = quizAnalysisData.getData().get(MARY_ANN.getUsername());
        questions.stream().forEach(question -> {
            String stat = maryAnnData.getStats().get("Indecisiveness Score for Q" + question.getQuestionNum());
            assertThat("Missing participant indecisiveness score for question #" + question.getQuestionNum(),
                        stat,
                        notNullValue());
            int score = Integer.parseInt(stat);
            assertThat("Incorrect participant indecisiveness score for question #" + question.getQuestionNum(),
                        score,
                        is(question.getQuestionNum()-1));
        });
    }

    @Test
    public void testTotalGuessesPerParticipant() {
        QuizParticipantAnalysisData gingerData = quizAnalysisData.getData().get(GINGER.getUsername());
        String stat = gingerData.getStats().get("Total Num Guesses");
        assertThat("Missing total number of guesses stat for Ginger", stat, notNullValue());
        int numGuesses = Integer.parseInt(stat);
        assertThat("Incorrect number of guesses for Ginger", numGuesses, is(3));

        QuizParticipantAnalysisData maryAnnData = quizAnalysisData.getData().get(MARY_ANN.getUsername());
        stat = maryAnnData.getStats().get("Total Num Guesses");
        assertThat("Missing total number of guesses stat for Mary Ann", stat, notNullValue());
        numGuesses = Integer.parseInt(stat);
        assertThat("Incorrect number of guesses for Mary Ann", numGuesses, is(6));
    }

    @Test
    public void testIndecisivenessPerParticipant() {
        QuizParticipantAnalysisData gingerData = quizAnalysisData.getData().get(GINGER.getUsername());
        String stat = gingerData.getStats().get("Indecisiveness Score");
        assertThat("Missing overall indecisiveness stat for Ginger", stat, notNullValue());
        int numGuesses = Integer.parseInt(stat);
        assertThat("Incorrect overall indecisiveness for Ginger", numGuesses, is(0));

        QuizParticipantAnalysisData maryAnnData = quizAnalysisData.getData().get(MARY_ANN.getUsername());
        stat = maryAnnData.getStats().get("Indecisiveness Score");
        assertThat("Missing overall indecisiveness stat for Mary Ann", stat, notNullValue());
        numGuesses = Integer.parseInt(stat);
        assertThat("Incorrect overall indecisiveness for Mary Ann", numGuesses, is(3));
    }

    private static QuizAnalysisData quizAnalysisData;
    private static List<Question> questions;
    private static final Member MARY_ANN = new MockMember("Mary Ann"),
                                GINGER = new MockMember("Ginger"),
                                MRS_HOWELL = new MockMember("Mrs. Howell");
    private static final String OWNER_ID = MRS_HOWELL.getUsername(),
                                QUIZ_ID = "123",
                                DECK_ID = "ABC";
}
