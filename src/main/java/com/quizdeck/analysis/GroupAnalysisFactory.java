package com.quizdeck.analysis;

import com.quizdeck.analysis.exceptions.AnalysisClassException;
import com.quizdeck.analysis.exceptions.AnalysisConstructionException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import com.quizdeck.model.database.CompleteQuiz;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 3/30/2016.
 */
public class GroupAnalysisFactory {

    public StaticAnalysis getAnalysisUsing(GroupAnalysisAlgorithm algorithm) throws AnalysisClassException, InsufficientDataException, AnalysisConstructionException {
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
            Object o = constructor.newInstance(groupName, completedQuizzes);
            clear();
            return (StaticAnalysis) o;
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new AnalysisConstructionException(e.getMessage(), e);
        }
    }


    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void setCompletedQuizzes(List<CompleteQuiz> completedQuizzes) {
        this.completedQuizzes.clear();
        this.completedQuizzes.addAll(completedQuizzes);
    }

    public void clear() {
        groupName = null;
        completedQuizzes.clear();
    }

    private Class[] getConstructorParameters() throws InsufficientDataException {
        LinkedList<String> missing = new LinkedList<>();
        if(groupName == null)
            missing.addLast("groupName");
        if(completedQuizzes.isEmpty())
            missing.addLast("completedQuizzes");

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

        return new Class[]{String.class, List.class};
    }

    private String groupName;
    private List<CompleteQuiz> completedQuizzes = new LinkedList<>();
}
