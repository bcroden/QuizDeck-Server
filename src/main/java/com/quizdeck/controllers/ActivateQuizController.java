package com.quizdeck.controllers;

import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.database.User;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.repositories.UserRepository;
import com.quizdeck.services.RedisActiveQuiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * Enable answer submissions, notify users
 * and in general activate the quiz
 *
 * Created by Cade on 3/12/2016.
 */

@RestController
@RequestMapping("/rest/secure/quiz")
public class ActivateQuizController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @RequestMapping(value="/activate/{quizId}", method= RequestMethod.GET)
    public ResponseEntity<String> activateQuiz(@PathVariable String quizId){

        Quiz newQuiz = quizRepository.findById(quizId);

        //TODO: Figure out if we are sending notifications, and how
        User owner = userRepository.findByUserName(newQuiz.getOwner());
        List<String> subscribedUsers = owner.getSubscriptions();
        //notify everyone on list? idk



        //enables submissions to this quizId
        ActiveQuiz activeQuiz = new ActiveQuiz(new Date(), true);
        redisActiveQuiz.addEntry(quizId, activeQuiz);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
