package com.quizdeck.analysis.outputs;

import com.quizdeck.analysis.inputs.Member;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all of the sample data and calculated statistics from each of the participants
 * of a quiz and overall statistics for the quiz itself.
 *
 * @author Alex
 */
public class QuizAnalysisData implements AnalysisResult<Member, QuizParticipantAnalysisData, String> {

    @Override
    public Map<Member, QuizParticipantAnalysisData> getData() {
        return participantAnalysisData;
    }

    @Override
    public Map<String, String> getStats() {
        return stats;
    }

    public void setData(Member member, QuizParticipantAnalysisData data) {
        participantAnalysisData.put(member, data);
    }
    public void setStat(String key, String value) {
        stats.put(key, value);
    }

    private Map<Member, QuizParticipantAnalysisData> participantAnalysisData = new HashMap<>();
    private Map<String, String> stats = new HashMap<>();
}
