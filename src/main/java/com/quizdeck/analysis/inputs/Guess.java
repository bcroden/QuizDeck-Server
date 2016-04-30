package com.quizdeck.analysis.inputs;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Represents a guess submitted by a participant to a question at a particular time.
 *
 * @author Alex
 */
@Getter
@Setter
public class Guess implements Serializable{
    /**
     * Default constructor to allow Jackson to deserialize Guess objects for unit tests
     */
    public Guess() {

    }

    /**
     * Initializes this Guess's selection and time stamp
     * @param selection The selection guessed by the participant
     * @param timeStamp The time at which the guess was submitted
     */
    public Guess(Selection selection, long timeStamp, int questionNum) {
        this.selection = selection;
        this.timeStamp = timeStamp;
        this.questionNum = questionNum;
    }

    private Selection selection;
    private long timeStamp;
    private int questionNum;
}
