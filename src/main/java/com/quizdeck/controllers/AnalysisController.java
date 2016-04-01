package com.quizdeck.controllers;

import com.quizdeck.analysis.Analysis;
import com.quizdeck.analysis.QuizAnalysisAlgorithm;
import com.quizdeck.analysis.QuizAnalysisFactory;
import com.quizdeck.analysis.exceptions.*;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.inputs.AccuracyInput;
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
@RequestMapping("/rest/secure/analysis/")
public class AnalysisController {

    @Autowired
    CompletedQuizRepository completedQuizRepository;

    @RequestMapping(value="accuracy/", method = RequestMethod.GET)
    public AnalysisResult accuracyResponse (@Valid @RequestBody AccuracyInput input, BindingResult result) throws InvalidJsonException, AnalysisException {
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        return processQuizWith(input.getId(), QuizAnalysisAlgorithm.ACCURACY);
    }

    @RequestMapping(value="indecisiveness/", method = RequestMethod.POST)
    public AnalysisResult indecisivenessResponse (@Valid @RequestBody AccuracyInput input, BindingResult result) throws InvalidJsonException, AnalysisException {
        if (result.hasErrors()) {
            throw new InvalidJsonException();
        }

        return processQuizWith(input.getId(), QuizAnalysisAlgorithm.INDECISIVENESS);
    }

    private AnalysisResult processQuizWith(String id, QuizAnalysisAlgorithm algorithm) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        CompleteQuiz completeQuiz = completedQuizRepository.findByQuizId(id);
        QuizAnalysisFactory factory = new QuizAnalysisFactory();

        factory.autoFillWith(completeQuiz);
        Analysis analysis = factory.getAnalysisUsing(algorithm);
        analysis.performAnalysis();

        return analysis.getResults();
    }
}
