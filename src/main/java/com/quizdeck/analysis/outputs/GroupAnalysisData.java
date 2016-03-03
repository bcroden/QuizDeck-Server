package com.quizdeck.analysis.outputs;

import java.util.HashMap;
import java.util.Map;

/**
 * Data class used to store the results of a group analysis algorithm
 *
 * @author Alex
 */
public class GroupAnalysisData implements AnalysisResult<String, GroupParticipantAnalysisData, String> {
    /**
     * Returns a mapping from user ids to an analysis of that participant's performance
     * @return  A mapping from user ids to an analysis of that participant's performance
     */
    @Override
    public Map<String, GroupParticipantAnalysisData> getData() {
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

    private Map<String, GroupParticipantAnalysisData> dataMap = new HashMap<>();
    private Map<String, String> statMap = new HashMap<>();
}
