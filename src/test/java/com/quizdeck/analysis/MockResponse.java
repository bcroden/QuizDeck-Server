package com.quizdeck.analysis;

import com.quizdeck.analysis.inputs.*;

/**
 * Mock Response class for testing
 *
 * @author Alex
 */
public class MockResponse implements Response
{
    public MockResponse(Member participant, Selection guess, Question question, long timeStamp) {
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
