package com.quizdeck.analysis;

import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.analysis.inputs.Member;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;

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
    public Object getAnalysisUsing(QuizAnalysis algorithm) throws AnalysisClassException, InsufficientDataException, AnalysisConstructionException {
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
            return constructor.newInstance(responses, questions, quizID, deckID, owner);
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new AnalysisConstructionException(e.getMessage(), e);
        }
    }

    public void setOwner(Member m) {
        owner = m;
    }
    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }
    public void setDeckID(String deckID) {
        this.deckID = deckID;
    }
    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }
    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    private Class[] getConstructorParameters() throws InsufficientDataException {
        LinkedList<String> missing = new LinkedList<>();
        if(owner == null)
            missing.addLast("Owner");
        if(quizID == null)
            missing.addLast("QuizID");
        if(deckID == null)
            missing.addLast("DeckID");
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

        return new Class[]{List.class, List.class, String.class, String.class, Member.class};
    }

    private Member owner;
    private String quizID, deckID;
    private List<Question> questions;
    private List<Response> responses;
}
