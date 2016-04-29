package com.quizdeck.controllers;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.database.submission;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
import com.quizdeck.services.RedisSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
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

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody SubmissionInput input, BindingResult result) throws InvalidJsonException, InactiveQuizException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        input.setQuestionNum(redisQuestion.getEntry(input.getQuizID()));

        //add newest submission for a specific active quiz only
        ActiveQuiz aQuiz = redisActiveQuiz.getEntry(input.getQuizID());
        if(aQuiz != null && aQuiz.isActive()) {
            List<? extends Submissions> submissions = new ArrayList<>();
            submissions = redisSubmissions.getAllSubmissions(input.getQuizID());
            int counter = 0;
            for(Submissions sub : submissions) {
                if(sub.getUserName().equals(input.getUserName())){
                    long index = submissions.indexOf(sub);
                    submission editEntry = (submission) redisSubmissions.getSubmission(input.getQuizID(), index);
                    //remove sub from redis
                    redisSubmissions.removeIndex(input.getQuizID(), index, editEntry);
                    if(sub.getQuestionNum() == input.getQuestionNum()){
                        //add guess to entry
                        List<Guess> guesses = editEntry.getGuesses();
                        input.getChoosenAnswer().setTimeStamp(System.currentTimeMillis());
                        guesses.add(input.getChoosenAnswer());
                        editEntry.setGuesses(guesses);
                        redisSubmissions.addSubmissionLink(input.getQuizID(), editEntry);
                        break;
                    }
                    else {
                        //manipulate object and return to redis
                        List<Guess> guesses = editEntry.getGuesses();
                        input.getChoosenAnswer().setTimeStamp(System.currentTimeMillis());
                        guesses.add(input.getChoosenAnswer());
                        editEntry.setGuesses(guesses);

                        //TODO: test this line well because it sure seems like magic
                        editEntry.setQuestion(quizRepository.findById(input.getQuizID()).getQuestions().get(input.getQuestionNum() - 1)); //minus one because quiz has normal counting and lists have 0 base

                        break;
                    }
                }
                counter++;
            }
            if(counter == redisSubmissions.getSize(input.getQuizID())){
                submission newEntry = new submission();
                newEntry.setUserName(input.getUserName());
                input.getChoosenAnswer().setTimeStamp(System.currentTimeMillis());
                List<Guess> guesses = new ArrayList<>();
                guesses.add(input.getChoosenAnswer());
                newEntry.setGuesses(guesses);
                newEntry.setQuestion(quizRepository.findById(input.getQuizID()).getQuestions().get(input.getQuestionNum() - 1));
                newEntry.setQuestionNum(redisQuestion.getEntry(input.getQuizID()));

                redisSubmissions.addSubmissionLink(input.getQuizID(), newEntry);
            }

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
