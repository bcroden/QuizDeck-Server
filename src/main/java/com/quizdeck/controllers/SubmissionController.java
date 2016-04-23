package com.quizdeck.controllers;

import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.database.submission;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
import com.quizdeck.services.RedisSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody SubmissionInput input, BindingResult result) throws InvalidJsonException, InactiveQuizException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        input.getQuestion().setQuestionNum(redisQuestion.getEntry(input.getQuizID()));

        //add newest submission for a specific active quiz only
        ActiveQuiz aQuiz = redisActiveQuiz.getEntry(input.getQuizID());
        if(aQuiz != null && aQuiz.isActive()) {
            redisSubmissions.addSubmissionLink(input.getQuizID(), new submission(input.getUserName(), input.getChoosenAnswers(), input.getQuestion()));
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else
            throw new InactiveQuizException();
    }

    @RequestMapping(value="/viewSubmissions/{quizId}", method=RequestMethod.GET)
    public List<? extends Submissions> getSubmissions(@PathVariable String quizId){
        return redisSubmissions.getAllSubmissions(quizId);
    }
}
