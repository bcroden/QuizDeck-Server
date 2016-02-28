package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.inputs.*;
import com.quizdeck.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Quiz> getQuizByOwner(@Valid @RequestBody OwnerQuizSearchInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        return quizRepository.findByOwner(input.getParameter());
    }

    @RequestMapping(value="/searchByOwnerTitle", method = RequestMethod.GET)
    public Quiz getQuizByOwnerAndTitle(@Valid @RequestBody OwnerTitleQuizSearchInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        return quizRepository.findByTitleAndOwner(input.getTitle(), input.getOwner());
    }

    @RequestMapping(value="/searchByOwnerLabels", method = RequestMethod.GET)
    public List<Quiz> getQuizByOwnerAndLabels (@Valid @RequestBody OwnerLabelsInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }
        return quizRepository.findByOwnerAndLabelsIn(input.getOwner(), input.getLabels());
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

    @RequestMapping(value="/quizLabelUpdate", method = RequestMethod.PUT)
    public ResponseEntity<String> quizLabelsUpdate(@Valid @RequestBody LabelUpdate input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        Quiz quiz = quizRepository.findByTitleAndOwner(input.getQuizTitle(), input.getUserName());
        for(String label : input.getLabels()){
            if(quiz.getLabels() == null || quiz.getLabels().isEmpty()){
                quiz.getLabels().addAll(input.getLabels());
                break;
            }
            else if(!quiz.getLabels().contains(label)){
                quiz.getLabels().add(label);
            }
        }
        quizRepository.save(quiz);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
