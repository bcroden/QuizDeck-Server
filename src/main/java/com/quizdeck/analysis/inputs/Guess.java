package com.quizdeck.analysis.inputs;

/**
 * Represents a guess submitted by a participant to a question at a particular time.
 *
 * @author Alex
 */
public class Guess {
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

    /**
     * Indicates the member's guess at the question's answer.
     * @return The member's guess at the question's answer
     */
    public Selection getSelection() {
        return selection;
    }

    /**
     * Indicates the time at which the member submitted its guess.
     * @return The time at which the member submitted its guess
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Indicates the question number to which the guess was registered
     * @return The question number to which the guess was registered
     */
    public int getQuestionNum() {
        return questionNum;
    }

    private Selection selection;
    private long timeStamp;
    private int questionNum;
}
