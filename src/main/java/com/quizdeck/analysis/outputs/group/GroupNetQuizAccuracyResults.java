package com.quizdeck.analysis.outputs.group;

import com.quizdeck.analysis.outputs.AnalysisResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data class used to store the results of the group accuracy analysis algorithm
 *
 * @author Alex
 */
public class GroupNetQuizAccuracyResults implements AnalysisResult<String, NetQuizData, String> {

    public GroupNetQuizAccuracyResults(List<String> labels) {
        this.labels = labels;
    }

    public List<String> getLabels() {
        return labels;
    }

    /**
     * Returns a mapping from user ids to an analysis of that participant's performance
     * @return  A mapping from user ids to an analysis of that participant's performance
     */
    @Override
    public Map<String, NetQuizData> getData() {
        return dataMap;
    }

    /**
     * Returns a map describing calculated statistics from the sample data for this group
     * @return  A map describing calculated statistics from the sample data for this group
     */
    @Override
    public Map<String, String> getStats() {
        return statMap;
    }

    private Map<String, NetQuizData> dataMap = new HashMap<>();
    private Map<String, String> statMap = new HashMap<>();

    private List<String> labels;
}
