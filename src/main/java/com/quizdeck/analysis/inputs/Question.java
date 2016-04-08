package com.quizdeck.analysis.inputs;

/**
 * Represents a question within a quiz.
 *
 * @author Alex
 */

public abstract class Question implements Comparable<Question> {
    /**
     * Indicates the question's location within its quiz.
     * Each questions' number should be unique within a quiz.
     * This number will be used in any reports if needed.
     * @return A non-negative number representing the question's place in the quiz.
     */
    public abstract int getQuestionNum();

    /**
     * Indicates the correct answer for this question.
     * @return The correct answer for the question
     */
    public abstract Selection getCorrectAnswer();

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
