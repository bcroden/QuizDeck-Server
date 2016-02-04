package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.analysis.outputs.QuizAnalysisData;

import java.util.List;

/**
 * Algorithm for accessing the accuracy of quiz results.
 * The following outputs are returned through the AnalysisResults interface:
 * -> Final submission of each participant
 * -> Final grade of each participant
 * -> Percent of participants correct
 *
 * TODO: Implement this algorithm
 *
 * @author Alex
 */
class QuizAccuracyAlgorithm extends AbstractQuizAlgorithm implements StaticAnalysis {
    protected QuizAccuracyAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, Member owner) {
        super(responses, questions, quizID, deckID, owner);
    }

    @Override
    public boolean performAnalysis() {
        //TODO: Implement this

        return false;
    }

    @Override
    public AnalysisResult getResults() throws AnalysisResultsUnavailableException {
        if(isAnalysisComplete)
            return quizOutputData;
        else
            throw new AnalysisResultsUnavailableException("Analysis has not been performed");
    }

    private boolean isAnalysisComplete = false;
    private QuizAnalysisData quizOutputData = new QuizAnalysisData();
}
