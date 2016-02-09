package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Selection;

/**
 * Mock Question class for testing
 *
 * @author Alex
 */
public class MockQuestion implements Question
{
    public MockQuestion(int questionNumber, Selection answer) {
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

        MockQuestion that = (MockQuestion) o;

        return questionNumber == that.questionNumber && (ANSWER != null ? ANSWER.equals(that.ANSWER) : that.ANSWER == null);

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
