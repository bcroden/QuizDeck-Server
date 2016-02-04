package com.quizdeck.analysis.outputs;

import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Selection;

import java.util.HashMap;
import java.util.Map;

/**
 * Contains all of the sample data and calculated statistics for a participant in a given quiz
 *
 * @author Alex
 */
public class QuizParticipantAnalysisData implements AnalysisResult<Question, Selection, String> {

    @Override
    public Map<Question, Selection> getData() {
        return sampleData;
    }

    @Override
    public Map<String, String> getStats() {
        return stats;
    }

    public void addGuess(Question question, Selection selection) {
        sampleData.put(question, selection);
    }

    public void addStat(String key, String value) {
        stats.put(key, value);
    }

    private Map<Question, Selection> sampleData = new HashMap<>();
    private Map<String, String> stats = new HashMap<>();
}
