package com.quizdeck.analysis.outputs;

import com.quizdeck.analysis.inputs.Question;

import java.util.*;

/**
 * Contains all of the sample data and calculated statistics from each of the participants
 * of a quiz and overall statistics for the quiz itself.
 *
 * @author Alex
 */
public class QuizAnalysisData implements AnalysisResult<String, QuizParticipantAnalysisData, String> {

    public QuizAnalysisData() {}
    /**
     * Initializes this dat block with the member who owns the quiz and its results along with
     * the quiz's identifier and the identifier of the deck to which it belongs
     *
     * @param ownerID     The member who owns the deck
     * @param deckID    The identifier of the deck to which the quiz belongs
     * @param quizID    The identifier of the quiz to which the analysis pertains
     */
    public QuizAnalysisData(String ownerID, String deckID, String quizID) {
        this.ownerID = ownerID;
        this.deckID = deckID;
        this.quizID = quizID;
    }

    /**
     * Returns a reference to a mapping which uses usernames to retrieve the sample data and
     * calculated statistics that participant submitted for this quiz.
     *
     * @return A mapping from a Member to a QuizParticipantAnalysisData object
     */
    @Override
    public Map<String, QuizParticipantAnalysisData> getData() {
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
     * Associates the provided QuizParticipantAnalysisData object with the given username
     *
     * @param username    Username to which the data should be attached
     * @param data      Analysis data regarding the username
     */
    public void putData(String username, QuizParticipantAnalysisData data) {
        participantAnalysisData.put(username, data);
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

    /**
     * Returns a reference to the member who owns this quiz and its analysis.
     * @return  member who owns the quiz
     */
    public String getOwnerID() {
        return ownerID;
    }

    /**
     * Returns a string which represents this quiz's identifier
     * @return  this quiz's identifier
     */
    public String getQuizID() {
        return quizID;
    }

    /**
     * Returns a string which represents the identifier for the deck to which this quiz belongs
     * @return  the identifier for the deck to which the quiz belongs
     */
    public String getDeckID() {
        return deckID;
    }

    /**
     * Set the list of questions associated with this quiz.
     * @param questions The list of questions associated with this quiz
     */
    public void setQuestions(List<Question> questions) {
        this.questions.clear();
        this.questions.addAll(questions);
    }

    /**
     * Return a list of questions associated with this quiz.
     * @return  The list of questions associated with this quiz
     */
    public List<Question> getQuestions() {
        return questions;
    }

    private Map<String, QuizParticipantAnalysisData> participantAnalysisData = new HashMap<>();
    private Map<String, String> stats = new HashMap<>();
    private List<Question> questions = new LinkedList<>();

    private String ownerID;
    private String quizID, deckID;
}
