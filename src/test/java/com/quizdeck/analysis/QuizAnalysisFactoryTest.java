package com.quizdeck.analysis;

import com.quizdeck.Application.QuizDeckApplication;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.exceptions.InsufficientDataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.LinkedList;

/**
 * Test for the QuizAnalysisFactory
 *
 * @author Alex
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
public class QuizAnalysisFactoryTest {
    @Test(expected = InsufficientDataException.class)
    public void insufficientDataTest() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        StaticAnalysis analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
    }

    @Test
    public void constructEmptyAnalysis() throws AnalysisException {
        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setOwner(new MockMember());
        factory.setDeckID("DeckID");
        factory.setQuizID("QuizID");
        factory.setQuestions(new LinkedList<>());
        factory.setResponses(new LinkedList<>());
        StaticAnalysis analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
    }
}

