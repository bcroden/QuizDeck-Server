package com.quizdeck.analysis;

/**
 * Represents all of the analysis algorithms available.
 *
 * @author Alex
 */
public enum QuizAnalysisAlgorithm {
    ACCURACY ("com.quizdeck.analysis.algorithms.quiz.QuizAccuracyAlgorithm"),
    INDECISIVENESS ("com.quizdeck.analysis.algorithms.quiz.QuizIndecisivenessAlgorithm");

    protected String getFullName() {
        return name;
    }
    private QuizAnalysisAlgorithm(String name) {
        this.name = name;
    }
    private String name;
}
