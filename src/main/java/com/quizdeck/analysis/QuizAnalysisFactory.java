package com.quizdeck.analysis;

import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import com.quizdeck.model.database.CompleteQuiz;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * Uses a modified Factory pattern to allow access to quiz level analysis algorithms.
 *
 * @author Alex
 */
public class QuizAnalysisFactory {
    @SuppressWarnings("unchecked")
    public QuizAnalysis getAnalysisUsing(QuizAnalysisAlgorithm algorithm) throws AnalysisClassException, InsufficientDataException, AnalysisConstructionException {
        Class theClass = null;
        try
        {
            theClass = Class.forName(algorithm.getFullName());
        }
        catch(ClassNotFoundException e)
        {
            throw new AnalysisClassException(e.getMessage(), e);
        }

        Constructor constructor;
        try
        {
            constructor = theClass.getDeclaredConstructor(getConstructorParameters());
        }
        catch(NoSuchMethodException e)
        {
            throw new AnalysisClassException(e.getMessage(), e);
        }

        try
        {
            constructor.setAccessible(true);
            Object o = constructor.newInstance(responses, questions, quizID, categories, ownerID);
            clear();
            return (QuizAnalysis) o;
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new AnalysisConstructionException(e.getMessage(), e);
        }
    }

    public void autoFillWith(CompleteQuiz completeQuiz) {
        setOwnerID(completeQuiz.getOwner());
        setCategories(completeQuiz.getQuiz().getCategories());
        setQuizID(completeQuiz.getQuizId());
        setResponses(completeQuiz.getSubmissions());
        setQuestions(completeQuiz.getQuiz().getQuestions());
    }

    public void setOwnerID(String id) {
        ownerID = id;
    }
    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
    public void setQuestions(List<? extends Question> questions) {
        this.questions = questions;
    }
    public void setResponses(List<?extends Response> responses) {
        this.responses = responses;
    }

    public void clear() {
        ownerID = null;
        quizID = null;
        categories = null;
        questions = null;
        responses = null;
    }

    private Class[] getConstructorParameters() throws InsufficientDataException {
        LinkedList<String> missing = new LinkedList<>();
        if(ownerID == null)
            missing.addLast("Owner");
        if(quizID == null)
            missing.addLast("QuizID");
        if(categories == null)
            missing.addLast("Categories");
        if(questions == null)
            missing.addLast("Questions");
        if(responses == null)
            missing.addLast("Responses");

        if(missing.size() > 0)
        {
            StringBuffer sb = new StringBuffer();
            for(int i = 0; i < missing.size(); i++)
            {
                if(i != 0)
                    sb.append(", ");
                sb.append(missing.get(i));
            }
            throw new InsufficientDataException(sb.toString());
        }

        return new Class[]{List.class, List.class, String.class, List.class, String.class};
    }

    private String ownerID;
    private String quizID;
    private List<String> categories;
    private List<? extends Question> questions;
    private List<? extends Response> responses;
}
