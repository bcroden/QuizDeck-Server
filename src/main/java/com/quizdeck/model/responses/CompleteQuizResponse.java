package com.quizdeck.model.responses;

/**
 * Created by Cade on 2/17/2016.
 */
public class CompleteQuizResponse {

    private String response;

    public CompleteQuizResponse(){ this.response = "good"; }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
