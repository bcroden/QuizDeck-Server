package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.*;
import com.quizdeck.model.inputs.CompleteQuizInput;
import com.quizdeck.repositories.CompletedQuizRepository;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.repositories.UserRepository;
import com.quizdeck.services.RedisActiveQuiz;
import com.quizdeck.services.RedisQuestion;
import com.quizdeck.services.RedisSubmissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */

@RestController
@RequestMapping("/rest/secure/quiz/")
public class ActiveQuizRequestController {

    @Autowired
    CompletedQuizRepository completeQuizRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RedisSubmissions redisSubmissions;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @Autowired
    RedisQuestion redisQuestion;

    @RequestMapping(value="/submit", method = RequestMethod.POST)
    public ResponseEntity<String> submitQuiz(@Valid @RequestBody CompleteQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        //submissions and start/stop time will have to be gathered from redis and assembled here, so only the quiz title, owner, and users taking it will be necessary
        //compile submissions from redis

        //will also close the quiz on redis, and add it to the database.
        List<? extends Submissions> subs = redisSubmissions.getAllSubmissionsAndRemove(input.getQuizId());
        //get active quiz information
        ActiveQuiz temp = redisActiveQuiz.getEntry(input.getQuizId());

        //--------------------------------------------------
        CompleteQuiz quiz = new CompleteQuiz(input.getQuiz(), temp.getStart(), temp.getStop(), input.getQuiz().getTitle(), input.getQuiz().getOwner(), subs);

        completeQuizRepository.save(quiz);

        //remove the active quiz entry from redis
        redisActiveQuiz.removeEntry(input.getQuizId());
        redisQuestion.removeEntry(input.getQuizId());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/activate/{quizId}", method= RequestMethod.GET)
    public ResponseEntity<String> activateQuiz(@PathVariable String quizId){

        Quiz newQuiz = quizRepository.findById(quizId);

        //TODO: Figure out if we are sending notifications, and how
        User owner = userRepository.findByUserName(newQuiz.getOwner());
        List<String> subscribedUsers = owner.getSubscriptions();
        //notify everyone on list? idk

        //set the question being answered to the first


        //enables submissions to this quizId
        ActiveQuiz activeQuiz = new ActiveQuiz(new Date(), true);
        redisActiveQuiz.addEntry(quizId, activeQuiz);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/deactivate/{quizId}", method= RequestMethod.GET)
    public ResponseEntity<String> deactivateQuiz(@PathVariable String quizId){

        //update redis entry
        ActiveQuiz temp = new ActiveQuiz();
        temp.setStop(new Date());
        temp.setActive(false);
        redisActiveQuiz.updateEntry(quizId, temp);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/questionIncrement/{quizId}", method = RequestMethod.GET)
    public ResponseEntity<String> questionIncrement(@PathVariable String quizId){
        int questionNum = 0;

        if(redisQuestion.getEntry(quizId)==null){
            redisQuestion.addEntry(quizId, 1);
        }
        else{
            questionNum = redisQuestion.getEntry(quizId);
            redisQuestion.updateEntry(quizId, ++questionNum);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/questionDecrement/{quizId}", method=RequestMethod.GET)
    public ResponseEntity<String> questionDecrement(@PathVariable String quizId){
        int questionNum = 0;

        if(redisQuestion.getEntry(quizId)==null){
            redisQuestion.addEntry(quizId, 1);
        }
        else{
            if(redisQuestion.getEntry(quizId) > 1)
                questionNum = redisQuestion.getEntry(quizId);
                redisQuestion.updateEntry(quizId, --questionNum);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
