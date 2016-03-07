package com.quizdeck.analysis.algorithms.quiz;

import com.quizdeck.analysis.inputs.GuessArrivalComparator;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.quiz.QuizParticipantAnalysisData;

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
class QuizAccuracyAlgorithm extends AbstractQuizAlgorithm {
    protected QuizAccuracyAlgorithm(List<Response> responses, List<Question> questions, String quizID, List<String> categories, String owner) {
        super(responses, questions, quizID, categories, owner);

        isAnalysisComplete = false;
    }

    @Override
    public boolean performAnalysis() {

        //Place sample data into data object
        for(String username : getQuizAnalysisData().getData().keySet()) {
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
                if(getQuizAnalysisData().getData().get(username) != null)
                    data = getQuizAnalysisData().getData().get(username);
                else
                    data = new QuizParticipantAnalysisData();
                data.addGuess(lastResponse.getQuestion(), lastResponse.getGuesses().get(0));
                getQuizAnalysisData().putData(username, data);
            }
        }

        //Calculate participant statistics
        int totNumCorrect = 0;
        for(String username : getQuizAnalysisData().getData().keySet()) {
            QuizParticipantAnalysisData participantData = getQuizAnalysisData().getData().get(username);
            int numCorrect = 0;
            for(Question question : getQuestions()) {
                if(participantData.getData().get(question.getQuestionNum()) != null && participantData
                        .getData()
                        .get(question.getQuestionNum())
                        .stream()
                        .anyMatch(guess -> guess.getSelection().equals(question.getCorrectAnswer())))
                    numCorrect++;
            }
            double percentCorrect = numCorrect / (double) getQuestions().size();
            participantData.putStat("Accuracy Percentage", Double.toString(percentCorrect));

            totNumCorrect += numCorrect; //accumulator for calculating quiz level statistics
        }

        double avNumCorrect = totNumCorrect / (double) (getQuestions().size() * getQuizAnalysisData().getData().keySet().size());

        getQuizAnalysisData().putStat("Average Accuracy Per Participant", Double.toString(avNumCorrect));

        isAnalysisComplete = true;

        return true;
    }

    @Override
    public boolean areResultsAvailable() {
        return isAnalysisComplete;
    }

    private boolean isAnalysisComplete;
}
