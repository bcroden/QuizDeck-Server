package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.Analysis;
import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.model.database.CompleteQuiz;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Base class for all group level analysis algorithms.
 *
 * @author Alex
 */
abstract class AbstractGroupAlgorithm implements Analysis {
    protected AbstractGroupAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        this.groupName = groupName;
        rawCompletedQuizzes = new LinkedList<>(completedQuizzes);
        allResponses = rawCompletedQuizzes.stream().map(CompleteQuiz::getSubmissions)
                                                    .flatMap(List::stream)
                                                    .collect(Collectors.toList());
        Set<String> userNames = allResponses.stream()
                                            .map(Response::getUserName)
                                            .collect(Collectors.toSet());
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
    protected String getGroupName() {
        return groupName;
    }
    protected List<CompleteQuiz> getRawCompletedQuizzes() {
        return rawCompletedQuizzes;
    }

    private String groupName;
    private List<CompleteQuiz> rawCompletedQuizzes;
    private List<Response> allResponses;
}
