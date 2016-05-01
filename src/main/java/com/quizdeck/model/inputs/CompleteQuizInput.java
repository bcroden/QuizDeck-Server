package com.quizdeck.model.inputs;

import com.quizdeck.model.database.Quiz;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * Created by Cade on 2/17/2016.
 */
@Getter
@Setter
public class CompleteQuizInput {

    @NotNull
    private Quiz quiz;

    @NotNull
    private String quizId;

    public CompleteQuizInput() {}

    public CompleteQuizInput(String quizId, Quiz quiz) {
        this.quizId = quizId;
        this.quiz = quiz;
    }


}
