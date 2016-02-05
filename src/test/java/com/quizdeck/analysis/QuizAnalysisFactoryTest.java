package com.quizdeck.analysis;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.*;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;

/**
 * Test for the QuizAnalysisFactory
 *
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
public class QuizAnalysisFactoryTest {
    @Test
    public void dummyTest() throws AnalysisException {
        LinkedList<Question> questions = new LinkedList<>();
        questions.add(new MultipleChoiceQuestion(1, new MultipleChoiceSelection('0')));
        questions.add(new MultipleChoiceQuestion(2, new MultipleChoiceSelection('1')));


        Student steve = new Student();
        LinkedList<Response> responses = new LinkedList<>();
        for(int i = 0; i < 50; i++)
            responses.add(new MultipleChoiceResponse(steve, new MultipleChoiceSelection(Integer.toString(i).charAt(0)), questions.get(0), i));

        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setResponses(responses);
        factory.setQuestions(questions);
        factory.setQuizID("Q1");
        factory.setDeckID("D1");
        factory.setOwner(steve);
        StaticAnalysis analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
        analysis.performAnalysis();
        QuizAnalysisData result = (QuizAnalysisData) analysis.getResults();
    }
}

//Mock classes
class MultipleChoiceQuestion implements Question
{
    public MultipleChoiceQuestion(int questionNumber, Selection answer) {
        this.questionNumber = questionNumber;
        ANSWER = answer;
    }
    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }
    @Override
    public Selection getAnswer() {
        return ANSWER;
    }
    @Override
    public int compareTo(Question that) {
        if(equals(that))
            return 0;
        if(getQuestionNumber() < that.getQuestionNumber())
            return -1;
        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultipleChoiceQuestion that = (MultipleChoiceQuestion) o;

        if (questionNumber != that.questionNumber) return false;
        return ANSWER != null ? ANSWER.equals(that.ANSWER) : that.ANSWER == null;

    }

    @Override
    public int hashCode() {
        int result = questionNumber;
        result = 31 * result + (ANSWER != null ? ANSWER.hashCode() : 0);
        return result;
    }

    private int questionNumber;
    private final Selection ANSWER;
}

class MultipleChoiceResponse implements Response
{
    public MultipleChoiceResponse(Member participant, Selection guess, Question question, long timeStamp) {
        this.participant = participant;
        this.guess = new Guess(guess, timeStamp);
        this.question = question;
    }

    @Override
    public Member getParticipant() {
        return participant;
    }
    @Override
    public Guess getGuess() {
        return guess;
    }
    @Override
    public Question getQuestion() {
        return question;
    }

    private Member participant;
    private Guess guess;
    private Question question;
}

class MultipleChoiceSelection implements Selection
{
    public MultipleChoiceSelection(char selection)
    {
        SELECTION = selection;
    }

    @Override
    public String toDisplayString() {
        return Character.toString(SELECTION);
    }
    @Override
    public boolean isSameAs(Selection that) {
        return toDisplayString().equals(that.toDisplayString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MultipleChoiceSelection that = (MultipleChoiceSelection) o;

        return SELECTION == that.SELECTION;
    }

    @Override
    public int hashCode() {
        return (int) SELECTION;
    }

    private final char SELECTION;
}

class Student implements Member
{
    @Override
    public String getUsername() {
        return "Steve";
    }

    @Override
    public int compareTo(Member that) {
        if(equals(that))
            return 0;
        return getUsername().compareTo(that.getUsername());
    }

    @Override
    public boolean equals(Object o) {
        if(o == null)
            return false;

        if(!(o instanceof Student))
            return false;

        Student that = (Student) o;

        return getUsername().equals(that.getUsername());
    }
}