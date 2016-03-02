package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.inputs.QuizDeleteInput;
import com.quizdeck.model.inputs.QuizEditInput;
import com.quizdeck.repositories.QuizRepository;
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

    @RequestMapping(value="/searchByOwner", method = RequestMethod.GET)
    public List<Quiz> getQuizByOwner(@ModelAttribute("claims") Claims claims){
        return quizRepository.findByOwner(claims.get("user").toString());
    }

    @RequestMapping(value="/quizEdit", method = RequestMethod.PUT)
    public ResponseEntity<String> quizLabelsUpdate(@Valid @RequestBody QuizEditInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        //blanket update for the quiz that was previously saved
        quizRepository.save(input.getEditedQuiz());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/quizDelete", method = RequestMethod.DELETE)
    public ResponseEntity<String> quizDelete(@Valid @RequestBody  QuizDeleteInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        quizRepository.removeById(input.getId());

        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
