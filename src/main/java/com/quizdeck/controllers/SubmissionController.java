package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.submission;
import com.quizdeck.model.inputs.SubmissionInput;
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
 * Created by Cade on 3/10/2016.
 */

@RestController
@RequestMapping("/rest/secure/quiz")
public class SubmissionController {

    @Autowired
    RedisSubmissions redisSubmissions;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody SubmissionInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        //add newest submission for a specific active quiz only
        ActiveQuiz temp = redisActiveQuiz.getEntry(input.getQuizID());
        if(temp.isActive()) {
            redisSubmissions.addSubmissionLink(input.getQuizID(), new submission(input.getUserName(), input.getChoosenAnswers(), input.getQuestion()));
            redisActiveQuiz.addEntry(input.getQuizID(), temp);
        }
        else{
            redisActiveQuiz.addEntry(input.getQuizID(), temp);
        }
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
