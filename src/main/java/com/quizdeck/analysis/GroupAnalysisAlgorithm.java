package com.quizdeck.analysis;

/**
 * Represents all of the group analysis algorithms available.
 *
 * @author Alex
 */
public enum GroupAnalysisAlgorithm {
    ACCURACY("com.quizdeck.analysis.algorithms.group.GroupAccuracyAlgorithm");

    protected String getFullName() {
        return NAME;
    }
    private GroupAnalysisAlgorithm(String name) {
        this.NAME = name;
    }
    private final String NAME;
}
