package com.quizdeck.controllers;

import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    RedisQuestion redisQuestion;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    SubmissionBuilding submissionBuilding;

    @Autowired
    RedisShortCodes redisShortCodes;

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody SubmissionInput input, BindingResult result) throws InvalidJsonException, InactiveQuizException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        StringBuilder anonName = new StringBuilder();
        anonName.append("Anon:");
        anonName.append(input.getUserName());
        input.setUserName(anonName.toString());
        return submissionBuilding.buildSubmission(input);
    }

    @RequestMapping(value="/shortConvert/{shortCode}", method = RequestMethod.GET)
    public String convertShortCode(@PathVariable String shortCode){
        return "\"" + redisShortCodes.getEntry(shortCode) + "\"";
    }

}
