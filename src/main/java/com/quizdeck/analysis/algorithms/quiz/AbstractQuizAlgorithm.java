package com.quizdeck.analysis.algorithms.quiz;

import com.quizdeck.analysis.Analysis;
import com.quizdeck.analysis.QuizAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import com.quizdeck.analysis.outputs.QuizAnalysisResult;
import com.quizdeck.analysis.outputs.QuizParticipantAnalysisData;

import java.util.Collections;
import java.util.List;

/**
 * Base class for all quiz level analysis algorithms.
 *
 * @author Alex
 */
abstract class AbstractQuizAlgorithm implements QuizAnalysis {
    protected AbstractQuizAlgorithm(List<Response> responses, List<Question> questions, String quizID, List<String> categories, String ownerID)
    {
        this.responses = responses;
        Collections.sort(questions);
        this.questions = questions;
        this.quizID = quizID;
        this.categories = categories;
        this.ownerID = ownerID;

        quizAnalysisData = new QuizAnalysisData(getOwnerID(), getCategories(), getQuizID());
        quizAnalysisData.setQuestions(getQuestions());

        //Populate the quiz data list of participants
        getResponses().stream().forEach(response -> {
            if(getQuizAnalysisData().getData()
                    .keySet()
                    .stream()
                    .noneMatch(username -> username.equals(response.getUserName()))
                    )
                getQuizAnalysisData().putData(response.getUserName(), new QuizParticipantAnalysisData());
        });
    }

    public abstract boolean areResultsAvailable();

    @Override
    public final QuizAnalysisResult getResults() throws AnalysisResultsUnavailableException {
        if(areResultsAvailable())
            return getQuizAnalysisData();
        throw new AnalysisResultsUnavailableException("Analysis has not been performed");
    }

    public final QuizAnalysisData getQuizAnalysisData() {
        return quizAnalysisData;
    }

    public final String getOwnerID()
    {
        return ownerID;
    }
    public final String getQuizID()
    {
        return quizID;
    }
    public final List<String> getCategories()
    {
        return categories;
    }
    public final List<Question> getQuestions()
    {
        return questions;
    }
    public final List<Response> getResponses()
    {
        return responses;
    }

    private String ownerID;
    private String quizID;
    private List<String> categories;
    private List<Question> questions;
    private List<Response> responses;

    private QuizAnalysisData quizAnalysisData;
}
