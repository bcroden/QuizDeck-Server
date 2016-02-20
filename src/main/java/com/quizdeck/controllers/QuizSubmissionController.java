package com.quizdeck.controllers;

/*
 *  Object created for quizzes submitted when completed
 */

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.inputs.NewQuizInput;
import com.quizdeck.model.responses.QuizSubmissionResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/rest/quizdeck/")
public class QuizSubmissionController {
    @RequestMapping(value="/quizsubmission", method= RequestMethod.POST)
    public QuizSubmissionResponse quizSubmissionResponse(@Valid @RequestBody NewQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }



        return new QuizSubmissionResponse();
    }
}
