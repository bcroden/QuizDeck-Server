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
 * Uses a modified Factory pattern to allow access to group/label level analysis algorithms.
 *
 * @author Alex
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
            Object o = constructor.newInstance(labels, completedQuizzes);
            clear();
            return (StaticAnalysis) o;
        }
        catch(InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            throw new AnalysisConstructionException(e.getMessage(), e);
        }
    }


    public void setLabels(List<String> labels) {
        this.labels.clear();
        this.labels.addAll(labels);
    }
    public void setCompletedQuizzes(List<CompleteQuiz> completedQuizzes) {
        this.completedQuizzes.clear();
        this.completedQuizzes.addAll(completedQuizzes);
    }

    public void clear() {
        labels.clear();
        completedQuizzes.clear();
    }

    private Class[] getConstructorParameters() throws InsufficientDataException {
        LinkedList<String> missing = new LinkedList<>();
        if(labels.isEmpty())
            missing.addLast("labels");
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

        return new Class[]{List.class, List.class};
    }

    private List<String> labels = new LinkedList<>();
    private List<CompleteQuiz> completedQuizzes = new LinkedList<>();
}
