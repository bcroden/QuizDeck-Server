package com.quizdeck.analysis.outputs;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Selection;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Contains all of the sample data and calculated statistics for a participant in a given quiz
 *
 * @author Alex
 */
public class QuizParticipantAnalysisData implements AnalysisResult<Question, List<Guess>, String> {

    /**
     * Returns a reference to a mapping which uses questions in the quiz to retrieve the guess(es)
     * submitted by the participant to that particular question
     *
     * @return A mapping from Questions to a list of the participant's Selections
     */
    @Override
    public Map<Question, List<Guess>> getData() {
        return sampleData;
    }

    /**
     * Returns a reference to a mapping which uses Strings to retrieve the calculated
     * statistics for the participant.
     *
     * @return A mapping from a String identifier to a String representation of a statistic
     */
    @Override
    public Map<String, String> getStats() {
        return stats;
    }

    /**
     * Add to the list of guesses to a particular question by a participant
     *
     * @param question  The Question to which the guess was submitted
     * @param selection The Selection which the participant guessed
     */
    public void addGuess(Question question, Guess selection) {
        List<Guess> guesses = null;
        if(sampleData.containsKey(question))
            guesses = sampleData.get(question);
        else
            guesses = new LinkedList<>();
        guesses.add(selection);
        sampleData.put(question, guesses);
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

    private Map<Question, List<Guess>> sampleData = new HashMap<>();
    private Map<String, String> stats = new HashMap<>();
}
