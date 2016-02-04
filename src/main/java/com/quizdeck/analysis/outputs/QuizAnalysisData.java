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

    /**
     * Returns a reference to a mapping which uses Members to retrieve the sample data and
     * calculated statistics which that participant submitted for this quiz.
     *
     * @return A mapping from a Member to a QuizParticipantAnalysisData object
     */
    @Override
    public Map<Member, QuizParticipantAnalysisData> getData() {
        return participantAnalysisData;
    }

    /**
     * Returns a reference to a mapping which uses Strings to retrieve the calculated
     * statistics for the quiz.
     *
     * @return A mapping from a String identifier to a String representation of a statistic
     */
    @Override
    public Map<String, String> getStats() {
        return stats;
    }

    /**
     * Associates the provided QuizParticipantAnalysisData object with the given Member
     *
     * @param member    Member to which the data should be attached
     * @param data      Analysis data regarding the member
     */
    public void putData(Member member, QuizParticipantAnalysisData data) {
        participantAnalysisData.put(member, data);
    }

    /**
     * Add a calculated statistic
     *
     * @param key   Identifier of the statistic
     * @param value String representation of the statistic
     */
    public void putStat(String key, String value) {
        stats.put(key, value);
    }

    private Map<Member, QuizParticipantAnalysisData> participantAnalysisData = new HashMap<>();
    private Map<String, String> stats = new HashMap<>();
}
