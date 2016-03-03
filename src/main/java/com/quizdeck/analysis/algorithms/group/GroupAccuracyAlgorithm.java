package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.QuizAnalysisAlgorithm;
import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.model.database.CompleteQuiz;

import java.util.List;

/**
 * Performs accuracy analysis on each quiz in a group and calculates overall accuracy for the entire group
 *
 * @author Alex
 */
class GroupAccuracyAlgorithm extends AbstractGroupAlgorithm {
    protected GroupAccuracyAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes, QuizAnalysisAlgorithm quizAnalysisAlgorithm) throws InsufficientDataException, AnalysisClassException, AnalysisConstructionException, AnalysisResultsUnavailableException {
        super(groupName, completedQuizzes, quizAnalysisAlgorithm);
    }

    @Override
    public boolean performAnalysis() {
        return false;
    }

    @Override
    public AnalysisResult getResults() throws AnalysisResultsUnavailableException {
        throw new AnalysisResultsUnavailableException("Algorithm has not been defined");
    }
}
