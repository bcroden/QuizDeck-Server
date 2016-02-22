package com.quizdeck.analysis.inputs;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.quizdeck.model.database.Answers;
import com.quizdeck.model.database.Questions;

/**
 * Represents one of the possible answers to a question.
 *
 * @author Alex
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Answers.class, name = "Answers")})
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
