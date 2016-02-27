package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.Quiz;
import com.quizdeck.model.inputs.LabelUpdate;
import com.quizdeck.model.responses.QuizSubmissionResponse;
import com.quizdeck.repositories.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Cade on 2/27/2016.
 * Return a users requested quiz or quizzes
 */

@RestController
@RequestMapping("/rest/secure/quiz")
public class RequestQuizController {

    @Autowired
    QuizRepository quizRepository;



    @RequestMapping(value="/quizLabelUpdate", method = RequestMethod.PUT)
    public QuizSubmissionResponse quizLabelsUpdate(@Valid @RequestBody LabelUpdate input, BindingResult result) throws InvalidJsonException {
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
        return new QuizSubmissionResponse();
    }

}
