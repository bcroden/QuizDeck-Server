package com.quizdeck.analysis.algorithms.group;

import com.quizdeck.analysis.Analysis;
import com.quizdeck.analysis.QuizAnalysis;
import com.quizdeck.analysis.QuizAnalysisAlgorithm;
import com.quizdeck.analysis.QuizAnalysisFactory;
import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.AnalysisResultsUnavailableException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.analysis.outputs.GroupParticipantAnalysisData;
import com.quizdeck.analysis.outputs.QuizAnalysisResult;
import com.quizdeck.model.database.CompleteQuiz;
import com.quizdeck.model.database.submission;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Base class for all group level analysis algorithms.
 *
 * @author Alex
 */
abstract class AbstractGroupAlgorithm implements Analysis {
    protected AbstractGroupAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        this(groupName, completedQuizzes, null);
    }
    protected AbstractGroupAlgorithm(String groupName, List<CompleteQuiz> completedQuizzes, QuizAnalysisAlgorithm quizAnalysisAlgorithm) throws AnalysisClassException, AnalysisConstructionException, InsufficientDataException, AnalysisResultsUnavailableException {
        if(quizAnalysisAlgorithm != null) {
            quizAnalysisData = new LinkedList<>();
            QuizAnalysisFactory quizFactory = new QuizAnalysisFactory();
            for(CompleteQuiz completeQuiz : completedQuizzes) {
                quizFactory.autoFillWith(completeQuiz);
                QuizAnalysis analysis = quizFactory.getAnalysisUsing(quizAnalysisAlgorithm);
                analysis.performAnalysis();
                quizAnalysisData.add(analysis.getResults());
            }
        }

        this.groupName = groupName;
        rawCompletedQuizzes = new LinkedList<>(completedQuizzes);
        allResponses = rawCompletedQuizzes.stream().map(CompleteQuiz::getSubmissions)
                                                    .flatMap(List::stream)
                                                    .collect(Collectors.toList());
        Set<String> userNames = allResponses.stream()
                                            .map(Response::getUserName)
                                            .collect(Collectors.toSet());
        groupParticipantAnalysisDataMap = new HashMap<>(userNames.size());
        for(String username : userNames)
            groupParticipantAnalysisDataMap.put(username, new GroupParticipantAnalysisData());
    }

    protected List<Response> getAllResponses() {
        return allResponses;
    }

    protected String getGroupName() {
        return groupName;
    }

    protected List<CompleteQuiz> getRawCompletedQuizzes() {
        return rawCompletedQuizzes;
    }

    protected boolean hasQuizAnalysisData() {
        return quizAnalysisData != null;
    }

    protected List<QuizAnalysisResult> getQuizAnalysisData() throws InsufficientDataException {
        if(hasQuizAnalysisData())
            return quizAnalysisData;
        throw new InsufficientDataException("Analysis was not requested on individual quizzes");
    }

    private String groupName;
    private List<CompleteQuiz> rawCompletedQuizzes;
    private List<Response> allResponses;
    private List<QuizAnalysisResult> quizAnalysisData;
    Map<String, GroupParticipantAnalysisData> groupParticipantAnalysisDataMap;
}
