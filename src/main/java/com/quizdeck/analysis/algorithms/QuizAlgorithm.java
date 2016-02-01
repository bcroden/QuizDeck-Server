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
class QuizAlgorithm {
    protected QuizAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, Member owner)
    {
        this.responses = responses;
        this.questions = questions;
        this.quizID = quizID;
        this.deckID = deckID;
        this.owner = owner;
    }

    public Member getOwner()
    {
        return owner;
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

    private Member owner;
    private String quizID, deckID;
    private List<Question> questions;
    private List<Response> responses;
}
