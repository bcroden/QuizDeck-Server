package com.quizdeck.analysis.inputs;

/**
 * Represents one of the possible answers to a question.
 *
 * @author Alex
 */

public abstract class Selection {
    /**
     * This is used when a string representation of the selection is needed
     * to display answers/guesses in reports.
     * @return A string representation of the selection.
     */
    public abstract String getContent();

    public abstract boolean equals(Object o);
    public abstract int hashCode();
}
