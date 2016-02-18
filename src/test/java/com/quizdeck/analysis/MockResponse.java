package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.*;

import java.util.Arrays;
import java.util.List;

/**
 * Mock Response class for testing
 *
 * @author Alex
 */
public class MockResponse implements Response
{
    public MockResponse(Member participant, Selection guess, Question question, long timeStamp) {
        this.participant = participant;
        this.guess = new Guess(guess, timeStamp, question.getQuestionNum());
        this.question = question;
    }

    @Override
    public String getUserName() {
        return participant.getUsername();
    }
    @Override
    public List<Guess> getGuess() {
        return Arrays.asList(new Guess[]{guess});
    }
    @Override
    public Question getQuestion() {
        return question;
    }

    private Member participant;
    private Guess guess;
    private Question question;
}
