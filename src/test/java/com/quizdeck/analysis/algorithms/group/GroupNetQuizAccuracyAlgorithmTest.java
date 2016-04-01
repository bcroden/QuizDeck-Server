package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.GroupAnalysisAlgorithm;
import com.quizdeck.analysis.GroupAnalysisFactory;
import com.quizdeck.analysis.MockMember;
import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Selection;
import com.quizdeck.analysis.outputs.group.GroupNetQuizAccuracyResults;
import com.quizdeck.model.database.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Unit test for the GroupNetQuizAccuracyAlgorithm
 */
public class GroupNetQuizAccuracyAlgorithmTest {

    @BeforeClass
    public static void setup() throws AnalysisException {

        List<CompleteQuiz> completeQuizzes = new LinkedList<>();
        for(int i = 0; i < QUIZ_TITLE.length; i++)
            completeQuizzes.add(getCompleteQuiz(QUIZ_TITLE[i], NUM_QUESTIONS_IN_QUIZ[i]));

        GroupAnalysisFactory factory = new GroupAnalysisFactory();
        factory.setGroupName(LABEL);
        factory.setCompletedQuizzes(completeQuizzes);
        StaticAnalysis analysis = factory.getAnalysisUsing(GroupAnalysisAlgorithm.ACCURACY);
        analysis.performAnalysis();
        results = (GroupNetQuizAccuracyResults) analysis.getResults();
    }

    @Test
    public void testNumberOfQuestions() {
        for(int i = 0; i < QUIZ_TITLE.length; i++)
            assertThat("Incorrect number of questions", results.getData().get(QUIZ_TITLE[i]).getNumberOfQuestions(), is(NUM_QUESTIONS_IN_QUIZ[i]));

    }

    @Test
    public void testIndividualQuizAccuracy() {
        for(int i = 0; i  < QUIZ_TITLE.length; i++)
            assertThat("Incorrect individual net accuracy for quiz #" + i, results.getData().get(QUIZ_TITLE[i]).getNetAccuracy(), is(INDIVIDUAL_NET_ACCURACY_FOR_QUIZ[i]));
    }

    @Test
    public void testOverallAccuracy() {
        assertThat("Incorrect overall accuracy", results.getStats().get("Net Accuracy"), is(OVERALL_ACCURACY));
    }

    private static CompleteQuiz getCompleteQuiz(String title, int numQuestions) {
        List<Questions> questions = getNQuestions(numQuestions);
        List<String> categories = Collections.singletonList("Category1");
        Quiz quiz = new Quiz("owner", title, questions, Collections.singletonList(LABEL), categories);
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

    private static final String OVERALL_ACCURACY = "0.5";
    private static final double[] INDIVIDUAL_NET_ACCURACY_FOR_QUIZ = new double[]{0.5, 0.5, 0.5};
    private static final String[] QUIZ_TITLE = new String[]{"Quiz1Title", "Quiz2Title", "Quiz3Title"};
    private static final int[] NUM_QUESTIONS_IN_QUIZ = {3, 3, 3};
    private static final String LABEL = "The Label";
    private static final Member BILBO = new MockMember("Bilbo"),
                                GIMLI = new MockMember("Gimli");
    private static GroupNetQuizAccuracyResults results;
}
