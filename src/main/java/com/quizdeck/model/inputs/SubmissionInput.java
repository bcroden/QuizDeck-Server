package com.quizdeck.model.inputs;

import com.quizdeck.analysis.inputs.Guess;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 3/10/2016.
 */
@Getter
@Setter
public class SubmissionInput {
    private String quizID;

    private String userName;

    private Guess choosenAnswer;

    private int questionNum;
}
