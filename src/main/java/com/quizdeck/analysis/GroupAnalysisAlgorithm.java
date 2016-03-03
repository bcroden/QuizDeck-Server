package com.quizdeck.analysis;

/**
 * Created by Alex on 3/2/2016.
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
