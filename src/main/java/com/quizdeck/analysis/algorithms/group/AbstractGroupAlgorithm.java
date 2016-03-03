package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.Analysis;
import com.quizdeck.analysis.QuizAnalysisAlgorithm;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import com.quizdeck.model.database.CompleteQuiz;

import java.util.LinkedList;
import java.util.List;

/**
 * Base class for all group level analysis algorithms.
 *
 * @author Alex
 */
abstract class AbstractGroupAlgorithm implements Analysis{
    protected AbstractGroupAlgorithm(String groupName, List<CompleteQuiz> completeQuizs) {
        this(groupName, completeQuizs, null);
    }
    protected AbstractGroupAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes, QuizAnalysisAlgorithm quizAnalysisAlgorithm) {
        if(quizAnalysisAlgorithm != null) {
            //TODO: perform needed analysis and store in the appropriate list
        }

        this.groupName = groupName;
        rawCompletedQuizzes = new LinkedList<>(completedQuizzes);
    }

    protected String getGroupName() {
        return groupName;
    }

    protected List<CompleteQuiz> getRawCompletedQuizzes() {
        return rawCompletedQuizzes;
    }

    protected boolean hasQuizAnalysisData() {
        return quizAnalysisData != null;
    }

    protected List<QuizAnalysisData> getQuizAnalysisData() throws InsufficientDataException {
        if(hasQuizAnalysisData())
            return quizAnalysisData;
        throw new InsufficientDataException("Analysis was not requested on individual quizzes");
    }

    private String groupName;
    private List<CompleteQuiz> rawCompletedQuizzes;
    private List<QuizAnalysisData> quizAnalysisData;
}
