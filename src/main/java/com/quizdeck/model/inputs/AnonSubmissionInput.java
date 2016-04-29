package com.quizdeck.model.inputs;

import com.quizdeck.analysis.inputs.Guess;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
public class AnonSubmissionInput {
    private String quizID;

    private Guess choosenAnswer;

    private int questionNum;

}
