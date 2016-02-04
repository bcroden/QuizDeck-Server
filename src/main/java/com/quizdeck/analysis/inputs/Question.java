package com.quizdeck.analysis.inputs;

/**
 * Represents a question within a quiz.
 *
 * @author Alex
 */
public interface Question extends Comparable<Question> {
    /**
     * Indicates the question's location within its quiz.
     * Each questions' number should be unique within a quiz.
     * This number will be used in any reports if needed.
     * @return A non-negative number representing the question's place in the quiz.
     */
    public int getQuestionNumber();

    /**
     * Indicates the correct answer for this question.
     * @return The correct answer for the question
     */
    public Selection getAnswer();

    /**
     * Indicates if the Question passed into the function is equivalent
     * to this question.
     * @param that The question to which this question is being compared
     * @return True if the questions are equivalent, false otherwise.
     */
    public default boolean isSameAs(Question that) {
        return this.compareTo(that) == 0;
    }
}
