package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.analysis.outputs.group.GroupNetQuizAccuracyResults;
import com.quizdeck.model.database.CompleteQuiz;

import java.util.List;

/**
 * Performs accuracy analysis on each quiz in a group and calculates overall accuracy for the entire group
 *
 * @author Alex
 */
class GroupNetQuizAccuracyAlgorithm extends AbstractGroupAlgorithm {
    protected GroupNetQuizAccuracyAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes) throws InsufficientDataException, AnalysisClassException, AnalysisConstructionException, AnalysisResultsUnavailableException {
        super(groupName, completedQuizzes);

        resultsAvailable = false;
    }

    @Override
    public boolean performAnalysis() {
        return false;
    }

    @Override
    protected boolean areResultsAvailable() {
        return resultsAvailable;
    }

    @Override
    protected AnalysisResult getGroupAnalysisData() {
        return groupNetQuizAccuracyResults;
    }

    private boolean resultsAvailable;
    private GroupNetQuizAccuracyResults groupNetQuizAccuracyResults;
}
