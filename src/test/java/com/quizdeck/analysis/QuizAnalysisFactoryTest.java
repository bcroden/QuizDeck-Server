package com.quizdeck.analysis;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.LinkedList;

/**
 * Test for the QuizAnalysisFactory
 *
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class QuizAnalysisFactoryTest {

    /**
     * Ensure the correct exception is thrown when insufficient data is supplied
     */
    @Test(expected = InsufficientDataException.class)
    public void insufficientDataTest() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        StaticAnalysis analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
    }

    /**
     * Construct a simple analysis pipeline
     */
    @Test
    public void constructEmptyAnalysis() throws AnalysisException {
        QuizAnalysisFactory factory = getFullFactory();
        Analysis analysis = factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
    }

    /**
     * Ensure that the factory clears itself after constructing a pipeline
     */
    @Test(expected = InsufficientDataException.class)
    public void doubleJeopardy1() throws AnalysisException {
        QuizAnalysisFactory factory = getFullFactory();
        factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
        factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);   //should throw exception
    }

    /**
     * Ensure that the factory clears itself when asked
     */
    @Test(expected = InsufficientDataException.class)
    public void doubleJeopardy2() throws AnalysisException {
        QuizAnalysisFactory factory = getFullFactory();
        factory.clear();
        factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);   //should throw exception
    }

    private QuizAnalysisFactory getFullFactory() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setOwnerID("Johnny Sock-o");
        factory.setCategories(new LinkedList<>());
        factory.setQuizID("QuizID");
        factory.setQuestions(new LinkedList<>());
        factory.setResponses(new LinkedList<>());
        return factory;
    }
}

