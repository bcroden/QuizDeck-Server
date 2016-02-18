package com.quizdeck.model.inputs;

import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.database.submission;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by Cade on 2/17/2016.
 */
public class CompleteQuizInput {

    @NotNull
    private Quiz quiz;

    @NotNull
    private List<submission> submissions;


    public CompleteQuizInput(Quiz quiz, List<submission> submissions) {
        this.quiz = quiz;
        this.submissions = submissions;
    }


}
