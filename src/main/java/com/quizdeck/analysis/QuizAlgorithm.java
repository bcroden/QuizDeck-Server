package com.quizdeck.analysis;

/**
 * Represents all of the analysis algorithms available.
 *
 * @author Alex
 */
public enum QuizAlgorithm {
    ACCURACY ("com.quizdeck.analysis.algorithms.QuizAccuracyAlgorithm"),
    INDECISIVENESS ("com.quizdeck.analysis.algorithms.QuizIndecisivenessAlgorithm");

    protected String getFullName() {
        return name;
    }
    private QuizAlgorithm(String name) {
        this.name = name;
    }
    private String name;
}
