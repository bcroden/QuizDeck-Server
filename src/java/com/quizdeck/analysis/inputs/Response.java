package com.quizdeck.analysis.inputs;

/**
 * Represents a submission given by a participant to a particular quiz.
 *
 * @author Alex
 */
public interface Response {
    /**
     * Indicates the member who submitted this response.
     * @return The member who submitted this response
     */
    public Member getParticipant();

    /**
     * Indicates the member's guess at the question's answer.
     * @return The member's guess at the question's answer
     */
    public Selection getGuess();

    /**
     * Indicates the question to which the member has submitted a guess.
     * @return The question to which the member has submitted a guess
     */
    public Question getQuestion();

    /**
     * Indicates the time at which the member submitted its guess.
     * @return The time at which the member submitted its guess
     */
    public long getTimeStamp();
}
