package com.quizdeck.model.inputs;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 5/1/2016.
 */
@Getter
@Setter
public class ActiveQuizInput {

    private String quizId;
    private boolean publicAvailable;

    public ActiveQuizInput(){}

}
