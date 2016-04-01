package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.inputs.GuessArrivalComparator;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.AnalysisResult;
import com.quizdeck.analysis.outputs.group.GroupNetQuizAccuracyResults;
import com.quizdeck.analysis.outputs.group.NetQuizData;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.database.submission;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Performs accuracy analysis on each quiz in a group and calculates overall accuracy for the entire group
 *
 * Data:    NetQuizData:    numberOfQuestions
 *                          netAccuracy         (Class average)
 *
 * Stat:    "Net Accuracy"  Unweighted average
 *
 * @author Alex
 */
class GroupNetQuizAccuracyAlgorithm extends AbstractGroupAlgorithm {
    protected GroupNetQuizAccuracyAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes) throws InsufficientDataException, AnalysisClassException, AnalysisConstructionException, AnalysisResultsUnavailableException {
        super(groupName, completedQuizzes);

        resultsAvailable = false;
        groupNetQuizAccuracyResults = new GroupNetQuizAccuracyResults();
    }

    @Override
    public boolean performAnalysis() {
        long totNumAvailable = 0, totNumCorrect = 0;
        for(CompleteQuiz completedQuiz : getRawCompletedQuizzes()) {
            Set<String> userNames =  completedQuiz.getSubmissions()
                                                    .stream()
                                                    .map(submission -> submission.getUserName())
                                                    .collect(Collectors.toSet());
            int numTotal = completedQuiz.getQuiz().getQuestions().size() * userNames.size();

            int numCorrect = 0;
            for(String userName : userNames) {
                //list of all responses submitted by this participant
                List<Response> responses = completedQuiz.getSubmissions()
                                                        .stream()
                                                        .filter(submission -> submission.getUserName().equals(userName))
                                                        .collect(Collectors.<Response>toList());
                for(Question question : completedQuiz.getQuiz().getQuestions()) {
                    //Was this participant's final guess correct?
                    boolean isCorrect = responses.stream()
                                                 .filter(response -> response.getQuestion().equals(question))
                                                 .map(Response::getGuesses)
                                                 .map(guesses -> {
                                                     Collections.sort(guesses, new GuessArrivalComparator(false));
                                                     return guesses.get(0);
                                                 })
                                                 .anyMatch(guess -> guess.getSelection().equals(question.getCorrectAnswer()));
                    //If the guess was correct increment the total number of correct guesses
                    if(isCorrect)
                        numCorrect++;
                }
            }

            totNumAvailable += numTotal;
            totNumCorrect += numCorrect;

            NetQuizData netQuizData = new NetQuizData();
            netQuizData.setNumberOfQuestions(completedQuiz.getQuiz().getQuestions().size());
            netQuizData.setNetAccuracy(numCorrect / (double) numTotal);

            groupNetQuizAccuracyResults.getData().put(completedQuiz.getQuiz().getTitle(), netQuizData);
        }

        groupNetQuizAccuracyResults.getStats().put("Net Accuracy", Double.toString(totNumCorrect / (double) totNumAvailable));
        resultsAvailable = true;
        return true;
    }

    @Override
    protected boolean areResultsAvailable() {
        return resultsAvailable;
    }

    @Override
    protected AnalysisResult getGroupAnalysisData() {
        return groupNetQuizAccuracyResults;
    }

    private boolean resultsAvailable;
    private GroupNetQuizAccuracyResults groupNetQuizAccuracyResults;
}
