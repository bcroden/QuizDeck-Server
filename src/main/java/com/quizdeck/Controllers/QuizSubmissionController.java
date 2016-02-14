package com.quizdeck.controllers;


import com.quizdeck.model.inputs.QuizSubmissionInput;
import com.quizdeck.model.responses.QuizSubmissionResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuizSubmissionController {

    public QuizSubmissionController(){
        //we will literally never build this!
        //except for testing things
    }

    @RequestMapping(value="quizdeck/quizsubmission", method= RequestMethod.POST)
    public QuizSubmissionResponse quizSubmissionResponse(@RequestBody QuizSubmissionInput input){


        return new QuizSubmissionResponse();
    }
}
