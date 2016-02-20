package com.quizdeck.controllers;

import com.quizdeck.analysis.Analysis;
import com.quizdeck.analysis.QuizAlgorithm;
import com.quizdeck.analysis.QuizAnalysisFactory;
import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.outputs.AnalysisResult;
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

/**
 * Created by Cade on 2/17/2016.
 */

@RestController
@RequestMapping("/rest/anaylsis/")
public class AnalysisController {

    @Autowired
    CompletedQuizRepository completedQuizRepository;

    @RequestMapping(value="accuracy/", method = RequestMethod.POST)
    public AnalysisResult accuracyResponse (@Valid @RequestBody AccuracyInput input, BindingResult result) throws InvalidJsonException, AnalysisException {
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
        CompleteQuiz quizForAnalysis = completedQuizRepository.findByTitleAndOwner(input.getTitle(), input.getOwner());

        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setOwnerID(input.getOwner());
        factory.setDeckID("Unknown deck ID");
        factory.setQuizID(quizForAnalysis.getQuiz().getId());
        factory.setResponses(quizForAnalysis.getSubmissions());
        factory.setQuestions(quizForAnalysis.getQuiz().getQuestions());

        Analysis analysis = factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
        analysis.performAnalysis();
        return analysis.getResults();
        //return new AccuracyResponse();
    }

    //other controllers can be created for different accuracy types

}
