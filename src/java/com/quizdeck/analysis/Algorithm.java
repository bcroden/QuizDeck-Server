package com.quizdeck.analysis;

/**
 * Represents all of the analysis algorithms available.
 */
public enum Algorithm {
    QUIZ_ACCURACY ("com.quizdeck.analysis.algorithms.QuizAccuracyAlgorithm");

    protected String getFullName() {
        return name;
    }
    private Algorithm(String name) {
        this.name = name;
    }
    private String name;
}
