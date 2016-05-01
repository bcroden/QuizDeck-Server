package com.quizdeck.model.inputs;

import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.database.submission;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

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

    @NotNull
    private List<submission> submissions;

    public CompleteQuizInput() {}

    public CompleteQuizInput(String quizId, Quiz quiz, List<submission> submissions) {
        this.quizId = quizId;
        this.quiz = quiz;
        this.submissions = submissions;
    }


}
