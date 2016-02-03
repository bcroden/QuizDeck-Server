package com.quizdeck.Controllers;


import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.quizdeck.RequestResponseObjects.QuizSubmissionResponse;
import com.quizdeck.RequestInputObjects.QuizSubmissionInput;

public class QuizSubmissionController {

    public QuizSubmissionController(){
        //we will literally never build this!
        //except for testing things
    }

    @RequestMapping(value="quiz/submission", method= RequestMethod.POST)
    public QuizSubmissionResponse quizSubmissionResponse(@RequestBody QuizSubmissionInput input){


        return new QuizSubmissionResponse();
    }
}
