package com.quizdeck.analysis.inputs;

/**
 * Represents one of the possible answers to a question.
 *
 * @author Alex
 */
public interface Selection {
    /**
     * This is used when a string representation of the selection is needed
     * to display answers/guesses in reports.
     * @return A string representation of the selection.
     */
    public String toDisplayString();

    /**
     * Indicates if the Selection passed into the function is equivalent
     * to this Selection.
     * @param that The selection to which this selection is being compared
     * @return True if the selections are equivalent, false otherwise.
     */
    public boolean isSameAs(Selection that);
}
