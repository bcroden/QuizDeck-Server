package com.quizdeck.model.responses;

public class QuizSubmissionResponse{

    private String status;

    public QuizSubmissionResponse(){
        status = "good";
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
