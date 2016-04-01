package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.model.database.CompleteQuiz;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Base class for all group level analysis algorithms.
 *
 * @author Alex
 */
abstract class AbstractGroupAlgorithm implements StaticAnalysis {
    protected AbstractGroupAlgorithm(List<String> labels, List<CompleteQuiz> completedQuizzes) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        this.labels.addAll(labels);
        rawCompletedQuizzes = new LinkedList<>(completedQuizzes);
        allResponses = rawCompletedQuizzes.stream().map(CompleteQuiz::getSubmissions)
                                                    .flatMap(List::stream)
                                                    .collect(Collectors.toList());
    }

    protected abstract AnalysisResult getGroupAnalysisData();
    protected abstract boolean areResultsAvailable();

    @Override
    public final AnalysisResult getResults() throws AnalysisResultsUnavailableException {
        if(areResultsAvailable())
            return getGroupAnalysisData();
        throw new AnalysisResultsUnavailableException("Analysis has not been performed");
    }

    protected List<Response> getAllResponses() {
        return allResponses;
    }
    protected List<String> getLabels() {
        return labels;
    }
    protected List<CompleteQuiz> getRawCompletedQuizzes() {
        return rawCompletedQuizzes;
    }

    private List<String> labels = new LinkedList<>();
    private List<CompleteQuiz> rawCompletedQuizzes;
    private List<Response> allResponses;
}
