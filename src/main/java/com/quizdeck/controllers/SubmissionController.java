package com.quizdeck.controllers;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.exceptions.InactiveQuizException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.Answers;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.database.submission;
import com.quizdeck.model.inputs.SubmissionInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
import com.quizdeck.services.RedisSubmissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private Logger log = LoggerFactory.getLogger(SubmissionController.class);

    @RequestMapping(value="/submission", method= RequestMethod.POST)
    public ResponseEntity<String> insertSubmission(@Valid @RequestBody SubmissionInput input, BindingResult result) throws InvalidJsonException, InactiveQuizException{
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
                if(sub.getUserName().equals(input.getUserName())){
                    log.info(input.getUserName() + " has already submitted");

                    if(sub.getQuestionNum() == input.getQuestionNum()){
                        long index = submissions.indexOf(sub);
                        submission editEntry = (submission) redisSubmissions.getSubmission(input.getQuizID(), index);
                        //add guess to entry
                        List<Guess> guesses = editEntry.getGuesses();

                        Guess newGuess = new Guess();
                        newGuess.setTimeStamp(System.currentTimeMillis());
                        newGuess.setQuestionNum(input.getQuestionNum());
                        newGuess.setSelection(new Answers(input.getChosenAnswerContent(), input.getChosenAnswer()));
                        guesses.add(newGuess);
                        editEntry.setGuesses(guesses);
                        //remove sub from redis
                        redisSubmissions.removeIndex(input.getQuizID(), index, editEntry);
                        redisSubmissions.addSubmissionLink(input.getQuizID(), editEntry);
                        break;
                    }
                }
                counter++;
            }
            if(counter == redisSubmissions.getSize(input.getQuizID())){
                submission newEntry = new submission();
                newEntry.setUserName(input.getUserName());
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
