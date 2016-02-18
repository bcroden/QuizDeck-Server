package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.inputs.AccuracyInput;
import com.quizdeck.model.responses.AccuracyResponse;
import com.quizdeck.repositories.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Cade on 2/17/2016.
 */

@RestController
@RequestMapping("/rest/anaylsis/")
public class AnalysisController {

    @Autowired
    CompletedQuizRepository completedQuizRepository;

    @RequestMapping(value="accuracy/", method = RequestMethod.POST)
    public AccuracyResponse accuracyResponse (@Valid @RequestBody AccuracyInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        /**
         * use input object to identify and pull object from the repository
         * then any analysis can be called
         * only use the accuracy algorithm from this controller
         *
         * this returns a list of all quizzes of a particular title for a user
         */
        List<CompleteQuiz> quizForAnalysis = completedQuizRepository.findByTitleAndOwner(input.getTitle(), input.getOwner());



        return new AccuracyResponse();
    }

    //other controllers can be created for different accuracy types

}
