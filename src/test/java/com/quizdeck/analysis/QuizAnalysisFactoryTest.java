package com.quizdeck.analysis;

import com.quizdeck.Application.QuizDeckApplication;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.inputs.Selection;
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
        ExcelAnalysis analysis = (ExcelAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
        analysis.performAnalysis();
        analysis.toExcel("path/to/output/dir");

    }
}

//Mock classes
class MultipleChoiceQuestion implements Question
{
    public MultipleChoiceQuestion(int questionNumber, Selection answer) {
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
    public boolean isSameAs(Question that) {
        return getQuestionNumber() == that.getQuestionNumber() && getAnswer().isSameAs(getAnswer());
    }

    private int questionNumber;
    private final Selection ANSWER;
}

class MultipleChoiceResponse implements Response
{
    public MultipleChoiceResponse(Member participant, Selection guess, Question question, long timeStamp) {
        this.participant = participant;
        this.guess = guess;
        this.question = question;
        this.timeStamp = timeStamp;
    }

    @Override
    public Member getParticipant() {
        return participant;
    }
    @Override
    public Selection getGuess() {
        return guess;
    }
    @Override
    public Question getQuestion() {
        return question;
    }
    @Override
    public long getTimeStamp() {
        return timeStamp;
    }

    private Member participant;
    private Selection guess;
    private Question question;
    private long timeStamp;
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

    private final char SELECTION;
}

class Student implements Member
{
    @Override
    public String getUsername() {
        return "Steve";
    }
    @Override
    public boolean isSameAs(Member that) {
        return getUsername().equals(that.getUsername());
    }
}