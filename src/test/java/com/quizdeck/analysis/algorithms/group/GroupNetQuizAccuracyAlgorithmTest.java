package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.GroupAnalysisAlgorithm;
import com.quizdeck.analysis.GroupAnalysisFactory;
import com.quizdeck.analysis.MockMember;
import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Selection;
import com.quizdeck.analysis.outputs.group.GroupNetQuizAccuracyResults;
import com.quizdeck.model.database.*;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.*;

/**
 * Unit test for the GroupNetQuizAccuracyAlgorithm
 */
public class GroupNetQuizAccuracyAlgorithmTest {

    @BeforeClass
    public static void setup() throws AnalysisException {

        List<String> allLabels = new LinkedList<>();
        allLabels.add("Label1");
        allLabels.add("Label2");

        TARGET_LABELS.add(allLabels.get(0));

        List<CompleteQuiz> completeQuizzes = new LinkedList<>();
        for(int i = 0; i < QUIZ_TITLE.length; i++)
            completeQuizzes.add(getCompleteQuiz(QUIZ_TITLE[i], NUM_QUESTIONS_IN_QUIZ[i], allLabels, INDIVIDUAL_NET_ACCURACY_FOR_QUIZ[i]));

        GroupAnalysisFactory factory = new GroupAnalysisFactory();
        factory.setLabels(TARGET_LABELS);
        factory.setCompletedQuizzes(completeQuizzes);
        StaticAnalysis analysis = factory.getAnalysisUsing(GroupAnalysisAlgorithm.ACCURACY);
        analysis.performAnalysis();
        results = (GroupNetQuizAccuracyResults) analysis.getResults();
    }

    @Test
    public void testLabels() {
        List<String> resultLabels = results.getLabels();

        assertThat("Incorrect number of labels in result", resultLabels.size(), is(TARGET_LABELS.size()));

        TARGET_LABELS.forEach(label -> assertTrue("Results label list is missing " + label, resultLabels.contains(label)));
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

    private static CompleteQuiz getCompleteQuiz(String title, int numQuestions, List<String> labels, double accuracy) {
        List<Questions> questions = getNQuestions(numQuestions);
        List<String> categories = Collections.singletonList("Category1");
        Quiz quiz = new Quiz("owner", title, questions, labels, categories);
        List<submission> submissions = getSubmissionsForQuizWithAccuracyOf(quiz, accuracy);
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

    private static List<submission> getSubmissionsForQuizWithAccuracyOf(Quiz quiz, double accuracy) {
        List<submission> submissions = new LinkedList<>();

        int numQuestions = quiz.getQuestions().size();
        double numCorrectNeededExact = accuracy * numQuestions * MEMBERS.length;
        assertThat("Unattainable accuracy for " + quiz.getTitle() + " given number of questions and participants", Math.floor(numCorrectNeededExact), is(Math.ceil(numCorrectNeededExact)));
        int numCorrectNeeded = (int) numCorrectNeededExact;

        for(Questions question : quiz.getQuestions()) {
            for(Member member : MEMBERS) {
                List<Guess> guesses = (numCorrectNeeded-- > 0) ? getCorrectGuessesFor(question) : getIncorrectGuessFor(question);
                submission sub = new submission();
                sub.setQuestion(question);
                sub.setGuesses(guesses);
                sub.setUserName(member.getUsername());
                submissions.add(sub);
            }
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
    private static final double[] INDIVIDUAL_NET_ACCURACY_FOR_QUIZ = new double[]{0.25, 0.5, 0.75};
    private static final String[] QUIZ_TITLE = new String[]{"Quiz1Title", "Quiz2Title", "Quiz3Title"};
    private static final int[] NUM_QUESTIONS_IN_QUIZ = {6, 2, 4};
    private static final List<String> TARGET_LABELS = new LinkedList<>();
    private static final Member[] MEMBERS = new Member[]{new MockMember("Bilbo"), new MockMember("Gimli")};
    private static GroupNetQuizAccuracyResults results;
}
