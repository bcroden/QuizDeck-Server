package com.quizdeck.model.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 2/17/2016.
 */
@Getter
@Setter
public class CompleteQuizResponse {

    private String response;

    public CompleteQuizResponse(){ this.response = "good"; }
}
