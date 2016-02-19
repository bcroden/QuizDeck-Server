package com.quizdeck.analysis.inputs;

import java.util.List;

/**
 * Represents a submission given by a participant to a particular quiz.
 *
 * @author Alex
 */
public interface Response {
    /**
     * Indicates the user name of the participant who submitted this response.
     * @return The user name of the participant who submitted this response
     */
    public String getUserName();

    /**
     * Provides a list containing all of member's guesses to a question.
     * @return A list member's guess to the question
     */
    public List<Guess> getGuess();

    /**
     * Indicates the question to which the member has submitted a guess.
     * @return The question to which the member has submitted a guess
     */
    public Question getQuestion();
}
