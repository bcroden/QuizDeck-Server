package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.database.submission;
import com.quizdeck.model.inputs.CompleteQuizInput;
import com.quizdeck.repositories.CompletedQuizRepository;
import com.quizdeck.services.RedisSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */

@RestController
@RequestMapping("/rest/secure/quiz/")
public class CompleteQuizSubmissionController {

    @Autowired
    CompletedQuizRepository completeQuizRepository;

    @Autowired
    RedisSubmissions redisSubmissions;

    @RequestMapping(value="/submit", method = RequestMethod.POST)
    public ResponseEntity<String> submitQuiz(@Valid @RequestBody CompleteQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        //submissions and start/stop time will have to be gathered from redis and assembled here, so only the quiz title, owner, and users taking it will be necessary
        //compile submissions from redis

        //will also close the quiz on redis, and add it to the database.
        List<submission> subs = redisSubmissions.getAllSubmissions(input.getQuizId());
        completeQuizRepository.save(new CompleteQuiz());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
