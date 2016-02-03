package com.quizdeck.analysis;

/**
 * Represents all of the analysis algorithms available.
 *
 * @author Alex
 */
public enum QuizAnalysis {
    ACCURACY ("com.quizdeck.analysis.algorithms.QuizAccuracyAlgorithm");

    protected String getFullName() {
        return name;
    }
    private QuizAnalysis(String name) {
        this.name = name;
    }
    private String name;
}
