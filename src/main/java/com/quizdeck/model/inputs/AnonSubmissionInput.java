package com.quizdeck.model.inputs;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
public class AnonSubmissionInput {
    private String quizID;

    private String chosenAnswer;

    private String chosenAnswerContent;

    private int questionNum;
}
