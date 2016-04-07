package com.quizdeck.controllers;

import com.quizdeck.analysis.*;
import com.quizdeck.analysis.exceptions.*;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.inputs.OwnerLabelsInput;
import com.quizdeck.repositories.CompletedQuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Cade on 2/17/2016.
 */

@RestController
@RequestMapping("/rest/secure/analysis/")
public class AnalysisController {

    @Autowired
    CompletedQuizRepository completedQuizRepository;

    @RequestMapping(value="accuracy/{quizId}", method = RequestMethod.GET)
    public AnalysisResult accuracyResponse (@PathVariable String quizId) throws AnalysisException{
        return processQuizWith(quizId, QuizAnalysisAlgorithm.ACCURACY);
    }

    @RequestMapping(value="indecisiveness/{quizId}", method = RequestMethod.GET)
    public AnalysisResult indecisivenessResponse (@PathVariable String quizId) throws AnalysisException {
        return processQuizWith(quizId, QuizAnalysisAlgorithm.INDECISIVENESS);
    }

    private AnalysisResult processQuizWith(String id, QuizAnalysisAlgorithm algorithm) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        CompleteQuiz completeQuiz = completedQuizRepository.findByQuizId(id);
        QuizAnalysisFactory factory = new QuizAnalysisFactory();

        factory.autoFillWith(completeQuiz);
        Analysis analysis = factory.getAnalysisUsing(algorithm);
        analysis.performAnalysis();

        return analysis.getResults();
    }

    @RequestMapping(value="label/net-accuracy/", method = RequestMethod.POST)
    public AnalysisResult groupNetAccuracyResponse(@Valid @RequestBody OwnerLabelsInput input, BindingResult result) throws InvalidJsonException, InsufficientDataException, AnalysisClassException, AnalysisConstructionException, AnalysisResultsUnavailableException {
        if(result.hasErrors())
            throw new InvalidJsonException();

        return processGroupWith(input.getOwner(), input.getLabels(), GroupAnalysisAlgorithm.ACCURACY);
    }

    private AnalysisResult processGroupWith(String owner, List<String> labels, GroupAnalysisAlgorithm algorithm) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        List<CompleteQuiz> quizList = completedQuizRepository.findByOwnerAndLabelsIn(owner, labels);
        GroupAnalysisFactory factory = new GroupAnalysisFactory();

        factory.setCompletedQuizzes(quizList);
        factory.setLabels(labels);
        Analysis analysis = factory.getAnalysisUsing(algorithm);
        analysis.performAnalysis();

        return analysis.getResults();
    }
}
