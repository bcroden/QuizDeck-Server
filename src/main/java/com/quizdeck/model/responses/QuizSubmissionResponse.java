package com.quizdeck.model.responses;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuizSubmissionResponse{

    private String status;

    public QuizSubmissionResponse(){
        status = "good";
    }
}
