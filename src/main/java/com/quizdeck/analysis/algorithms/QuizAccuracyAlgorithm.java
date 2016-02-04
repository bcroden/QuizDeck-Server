package com.quizdeck.analysis.algorithms;

import com.quizdeck.analysis.StaticAnalysis;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
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
 * -> Final submission of each participant  //TODO: Needs to be tested
 * -> Final grade of each participant       //TODO: Needs to be tested
 * -> Percent of participants correct       //TODO: Needs to be tested (May not be correct)
 *
 * //TODO: Test algorithm speed
 *
 * Important Notes:
 *      -> For each question answered by a participant, only the response with the latest time stamp will be considered.
 *
 * Testing Notes:
 *      -> Test case when a participant answers some but not all of the questions.
 *
 * @author Alex
 */
class QuizAccuracyAlgorithm extends AbstractQuizAlgorithm implements StaticAnalysis {
    protected QuizAccuracyAlgorithm(List<Response> responses, List<Question> questions, String quizID, String deckID, Member owner) {
        super(responses, questions, quizID, deckID, owner);
    }

    @Override
    public boolean performAnalysis() {

        quizOutputData = new QuizAnalysisData();

        //Populate the quiz data list of participants
        getResponses().stream().forEach(response -> {
            if(quizOutputData.getData().keySet().stream().noneMatch(member -> member.isSameAs(response.getParticipant())))
                quizOutputData.putData(response.getParticipant(), null);
        });

        //Place sample data into data object
        for(Member participant : quizOutputData.getData().keySet()) {
            //Get the last responses for this participant
            Response lastResponse = getResponses().stream()
                    .filter(response -> participant.isSameAs(response.getParticipant()))    //responses by this participant
                    .reduce(null, (acc, itr) -> {   //get the response with the latest time stamp
                        if (acc == null || acc.getGuess().getTimeStamp() < itr.getGuess().getTimeStamp())
                            return itr;
                        return acc;
                    });

            if (lastResponse == null)
                continue;

            QuizParticipantAnalysisData data = new QuizParticipantAnalysisData();
            data.addGuess(lastResponse.getQuestion(), lastResponse.getGuess());
            quizOutputData.putData(participant, data);
        }

        //Calculate participant statistics
        int totNumCorrect = 0;
        for(Member participant : quizOutputData.getData().keySet()) {
            QuizParticipantAnalysisData participantData = quizOutputData.getData().get(participant);
            int numCorrect = 0;
            for(Question question : getQuestions()) {
                if(participantData.getData().get(question).stream().anyMatch(guess -> guess.getSelection().isSameAs(question.getAnswer())))
                    numCorrect++;
            }
            double percentCorrect = numCorrect / (double) getQuestions().size();
            participantData.putStat("Overall Grade", Double.toString(percentCorrect));

            totNumCorrect += numCorrect; //accumulator for calculating quiz level statistics
        }

        double avNumCorrect = totNumCorrect / (double) quizOutputData.getData().keySet().size();
        double avPercentCorrect = avNumCorrect / (double) getQuestions().size();

        quizOutputData.putStat("Average Number of Correct Responses Per Participant", Double.toString(avNumCorrect));
        quizOutputData.putStat("Average Percentage of Accuracy Per Participant", Double.toString(avPercentCorrect));

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
