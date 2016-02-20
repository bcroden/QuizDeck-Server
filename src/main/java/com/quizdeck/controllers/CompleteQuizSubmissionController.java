package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.inputs.CompleteQuizInput;
import com.quizdeck.model.responses.CompleteQuizResponse;
import com.quizdeck.repositories.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Cade on 2/16/2016.
 */

@RestController
@RequestMapping("/rest/quiz/")
public class CompleteQuizSubmissionController {

    @Autowired
    CompletedQuizRepository completeQuizRepository;

    @RequestMapping(value="/submit", method = RequestMethod.POST)
    public CompleteQuizResponse submitQuiz(@Valid @RequestBody CompleteQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        //submissions and start/stop time will have to be gathered from redis and assembled here, so only the quiz title, owner, and users taking it will be necessary

        //will also close the quiz on redis, and add it to the database.

        //completeQuizRepository.save(new CompleteQuiz());

        return null;
    }

}
