package com.quizdeck.analysis.inputs;

import java.util.List;

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
    public List<String> getParticipant();

    /**
     * Indicates the member's guess at the question's answer.
     * @return The member's guess at the question's answer
     */
    public List<Guess> getGuess();

    /**
     * Indicates the question to which the member has submitted a guess.
     * @return The question to which the member has submitted a guess
     */
    public Question getQuestion();
}
