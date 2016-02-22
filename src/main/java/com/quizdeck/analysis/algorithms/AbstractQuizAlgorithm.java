package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;

import java.util.List;

/**
 * Base class for all quiz level analysis algorithms.
 *
 * @author Alex
 */
abstract class AbstractQuizAlgorithm {
    protected AbstractQuizAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, String ownerID)
    {
        this.responses = responses;
        this.questions = questions;
        this.quizID = quizID;
        this.deckID = deckID;
        this.ownerID = ownerID;
    }

    public String getOwnerID()
    {
        return ownerID;
    }
    public String getQuizID()
    {
        return quizID;
    }
    public String getDeckID()
    {
        return deckID;
    }
    public List<Question> getQuestions()
    {
        return questions;
    }
    public List<Response> getResponses()
    {
        return responses;
    }

    private String ownerID;
    private String quizID, deckID;
    private List<Question> questions;
    private List<Response> responses;
}
