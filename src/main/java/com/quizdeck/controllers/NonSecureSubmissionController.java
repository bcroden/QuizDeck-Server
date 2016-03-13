package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.AnonSubmission;
import com.quizdeck.model.inputs.AnonSubmissionInput;
import com.quizdeck.services.RedisActiveQuiz;
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

/**
 * This controller is for individuals that are not signed in
 * Taking a quiz under an anonymous, or temporary username
 *
 * Created by Cade on 3/12/2016.
 */

@RestController
@RequestMapping("/rest/nonsecure/quiz")
public class NonSecureSubmissionController {

    @Autowired
    RedisSubmissions redisSubmissions;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody AnonSubmissionInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        //add newest submission for a specific active quiz only
        ActiveQuiz temp = redisActiveQuiz.getEntry(input.getQuizID());
        if(temp.isActive()) {
            redisSubmissions.addAnonSubmissionLink(input.getQuizID(), new AnonSubmission(input.getChoosenAnswers(), input.getQuestion()));
            redisActiveQuiz.addEntry(input.getQuizID(), temp);
        }
        else{
            redisActiveQuiz.addEntry(input.getQuizID(), temp);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
