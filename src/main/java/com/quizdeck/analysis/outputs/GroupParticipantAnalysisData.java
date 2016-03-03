package com.quizdeck.analysis.outputs;

import java.util.Map;

/**
 * Data class used to store data and statistics calculated for a participant by a group
 * analysis algorithm
 *
 * @author Alex
 */
public class GroupParticipantAnalysisData implements AnalysisResult<String, QuizParticipantAnalysisData, String> {
    /**
     * Returns a mapping from quiz ids to an analysis of this participant's performance on that quiz
     * @return  A mapping from quiz ids to an analysis of this participant's performance
     */
    @Override
    public Map<String, QuizParticipantAnalysisData> getData() {
        return null;
    }

    /**
     * Returns a map describing calculated statistics from the sample data for this participant
     * @return  A map describing calculated statistics from the sample data for this participant
     */
    @Override
    public Map<String, String> getStats() {
        return null;
    }
}
