package com.quizdeck.controllers;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.*;
import com.quizdeck.model.inputs.AnonSubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
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
import java.util.ArrayList;
import java.util.List;

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

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody AnonSubmissionInput input, BindingResult result) throws InvalidJsonException, InactiveQuizException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        int currQuestionNum = redisQuestion.getEntry(input.getQuizID());
        input.setQuestionNum(currQuestionNum);
        //add newest submission for a specific active quiz only
        ActiveQuiz aQuiz = redisActiveQuiz.getEntry(input.getQuizID());
        if(aQuiz != null && aQuiz.isActive()) {
            List<submission> submissions = new ArrayList<>();
            for(int i = 0; i < redisSubmissions.getSize(input.getQuizID()); i++){
                submissions.add(((submission)(redisSubmissions.getAllSubmissions(input.getQuizID())).get(i)));
            }
            int counter = 0;
            for(Submissions sub : submissions) {
                    if(sub.getQuestionNum() == input.getQuestionNum()){
                        long index = submissions.indexOf(sub);
                        AnonSubmission editEntry = (AnonSubmission) redisSubmissions.getAnonSubmission(input.getQuizID(), index);
                        //remove sub from redis
                        redisSubmissions.removeIndex(input.getQuizID(), index, editEntry);
                        //add guess to entry
                        List<Guess> guesses = editEntry.getGuesses();
                        Guess newGuess = new Guess();
                        newGuess.setTimeStamp(System.currentTimeMillis());
                        newGuess.setQuestionNum(input.getQuestionNum());
                        newGuess.setSelection(new Answers(input.getChosenAnswerContent(), input.getChosenAnswer()));
                        guesses.add(newGuess);
                        editEntry.setGuesses(guesses);
                        redisSubmissions.addAnonSubmissionLink(input.getQuizID(), editEntry);
                        break;
                }
                counter++;
            }
            if(counter == redisSubmissions.getSize(input.getQuizID())){
                AnonSubmission newEntry = new AnonSubmission();
                Guess newGuess = new Guess();
                newGuess.setTimeStamp(System.currentTimeMillis());
                newGuess.setQuestionNum(input.getQuestionNum());
                newGuess.setSelection(new Answers(input.getChosenAnswerContent(), input.getChosenAnswer()));
                List<Guess> guesses = new ArrayList<>();
                guesses.add(newGuess);
                newEntry.setGuesses(guesses);
                if(quizRepository.findById(input.getQuizID())==null){
                    throw new InactiveQuizException();
                }
                newEntry.setQuestionNum(redisQuestion.getEntry(input.getQuizID()));
                newEntry.setQuestion(quizRepository.findById(input.getQuizID()).getQuestions().get(newEntry.getQuestionNum()));

                redisSubmissions.addAnonSubmissionLink(input.getQuizID(), newEntry);
            }

            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else
            throw new InactiveQuizException();
    }

}
