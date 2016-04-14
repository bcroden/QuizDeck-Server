package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.database.User;
import com.quizdeck.model.inputs.NewQuizInput;
import com.quizdeck.repositories.QuizRepository;
import com.quizdeck.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Cade on 2/27/2016.
 * Return a users requested quiz or quizzes
 */

@RestController
@RequestMapping("/rest/secure/quiz")
public class QuizRequestController {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="/searchBySelf", method = RequestMethod.GET)
    public List<Quiz> getQuizBySelf(@ModelAttribute("claims") Claims claims){
        return quizRepository.findByOwner(claims.get("user").toString());
    }

    @RequestMapping(value="/searchById/{quizId}", method=RequestMethod.GET)
    public Quiz getQuizById(@PathVariable String quizId){ return quizRepository.findById(quizId); }

    @RequestMapping(value="/quizEdit", method = RequestMethod.PUT)
    public ResponseEntity<String> quizLabelsUpdate(@Valid @RequestBody Quiz input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        //blanket update for the quiz that was previously saved
        quizRepository.save(input);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/quizDelete", method = RequestMethod.DELETE)
    public ResponseEntity<String> quizDelete(@PathVariable String quizId) {
        quizRepository.removeById(quizId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/quizsubmission", method= RequestMethod.POST)
    public ResponseEntity<String> quizSubmissionResponse(@Valid @RequestBody NewQuizInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        quizRepository.save(new Quiz(input.getOwner(), input.getTitle(), input.getQuestions(), input.getLabels(), input.getCategories(), input.isPublicAvailable()));

        //add any new labels to the list associated with the user
        User user = userRepository.findByUserName(input.getOwner().trim());
        if(user != null) {
            if (user.getLabels() != null && user.getLabels().size() > 0 && input.getLabels() != null && input.getLabels().size() > 0) {
                for (String label : input.getLabels()) {
                    if (!user.getLabels().contains(label)) {
                        user.getLabels().add(label);
                    }
                }
                userRepository.save(user);
            }
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
