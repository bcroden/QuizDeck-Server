package com.quizdeck.controllers;

import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
import com.quizdeck.services.RedisSubmissions;
import com.quizdeck.services.SubmissionBuilding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    RedisQuestion redisQuestion;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    SubmissionBuilding submissionBuilding;

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody SubmissionInput input, BindingResult result) throws InvalidJsonException, InactiveQuizException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        return submissionBuilding.buildSubmission(input);
    }

    @RequestMapping(value="/viewSubmissions/{quizId}", method=RequestMethod.GET)
    public List<? extends Submissions> getSubmissions(@PathVariable String quizId){
        return redisSubmissions.getAllSubmissions(quizId);
    }
}
