package com.quizdeck.analysis.inputs;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.cfg.MapperConfig;
import com.fasterxml.jackson.databind.introspect.AnnotatedField;
import com.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import com.quizdeck.model.database.Questions;

/**
 * Represents a question within a quiz.
 *
 * @author Alex
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Questions.class, name = "Questions")})
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
