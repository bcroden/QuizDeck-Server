package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.ExcelAnalysis;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;

import java.util.List;

/**
 * Algorithm for accessing the accuracy of quiz results.
 * TODO: Implement this algorithm
 *
 * @author Alex
 */
class QuizAccuracyAlgorithm extends QuizAlgorithm implements ExcelAnalysis {
    protected QuizAccuracyAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, Member owner) {
        super(responses, questions, quizID, deckID, owner);
    }

    @Override
    public boolean toExcel(String pathToDir) {
        //TODO: Implement this
        return false;
    }
    @Override
    public boolean performAnalysis() {
        //TODO: Implement this
        return false;
    }
    @Override
    public boolean hasPerformedAnalysis() {
        //TODO: Implement this
        return false;
    }
}
