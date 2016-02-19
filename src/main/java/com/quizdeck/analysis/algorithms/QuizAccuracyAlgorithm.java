package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.inputs.GuessArrivalComparator;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
import com.quizdeck.analysis.outputs.QuizParticipantAnalysisData;

import java.util.*;

/**
 * Algorithm for accessing the accuracy of quiz results.
 * The following outputs are returned through the AnalysisResults interface:
 * -> Final submission of each participant
 * -> Final grade of each participant
 * -> Percent of participants correct
 *
 * Important Notes:
 *      -> For each question answered by a participant, only the response with the latest time stamp will be considered.
 *
 * @author Alex
 */
class QuizAccuracyAlgorithm extends AbstractQuizAlgorithm implements StaticAnalysis {
    protected QuizAccuracyAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, Member owner) {
        super(responses, questions, quizID, deckID, owner);
        quizOutputData = new QuizAnalysisData(getOwner(), getDeckID(), getQuizID());
    }

    @Override
    public boolean performAnalysis() {

        //Populate the quiz data list of participants
        getResponses().stream().forEach(response -> {
            if(quizOutputData.getData()
                    .keySet()
                    .stream()
                    .noneMatch(username -> username.equals(response.getUserName()))
                    )
                quizOutputData.putData(response.getUserName(), new QuizParticipantAnalysisData());
        });

        //Place sample data into data object
        for(String username : quizOutputData.getData().keySet()) {
            for(Question question : getQuestions()) {
                //Get the last responses for this username
                Response lastResponse = getResponses().stream()
                        .filter(response -> username.equals(response.getUserName()))    //responses by this username
                        .filter(response -> question.equals(response.getQuestion()))
                        .reduce(null, (acc, itr) -> {   //get the response with the latest time stamp
                            Collections.sort(itr.getGuesses(), new GuessArrivalComparator(false));
                            if (acc == null || acc.getGuesses().get(0).getTimeStamp() < itr.getGuesses().get(0).getTimeStamp())
                                return itr;
                            return acc;
                        });

                if(lastResponse == null)
                    continue;

                QuizParticipantAnalysisData data = null;
                if(quizOutputData.getData().get(username) != null)
                    data = quizOutputData.getData().get(username);
                else
                    data = new QuizParticipantAnalysisData();
                data.addGuess(lastResponse.getQuestion(), lastResponse.getGuesses().get(0));
                quizOutputData.putData(username, data);
            }
        }

        //Calculate participant statistics
        int totNumCorrect = 0;
        for(String username : quizOutputData.getData().keySet()) {
            QuizParticipantAnalysisData participantData = quizOutputData.getData().get(username);
            int numCorrect = 0;
            for(Question question : getQuestions()) {
                if(participantData.getData().get(question) != null && participantData
                        .getData()
                        .get(question)
                        .stream()
                        .anyMatch(guess -> guess.getSelection().equals(question.getCorrectAnswer())))
                    numCorrect++;
            }
            double percentCorrect = numCorrect / (double) getQuestions().size();
            participantData.putStat("Accuracy Percentage", Double.toString(percentCorrect));

            totNumCorrect += numCorrect; //accumulator for calculating quiz level statistics
        }

        double avNumCorrect = totNumCorrect / (double) (getQuestions().size() * quizOutputData.getData().keySet().size());

        quizOutputData.putStat("Average Accuracy Per Participant", Double.toString(avNumCorrect));

        quizOutputData.setQuestions(getQuestions());

        isAnalysisComplete = true;

        return true;
    }

    @Override
    public AnalysisResult getResults() throws AnalysisResultsUnavailableException {
        if(isAnalysisComplete)
            return quizOutputData;
        else
            throw new AnalysisResultsUnavailableException("Analysis has not been performed");
    }

    private boolean isAnalysisComplete = false;
    private QuizAnalysisData quizOutputData;
}
