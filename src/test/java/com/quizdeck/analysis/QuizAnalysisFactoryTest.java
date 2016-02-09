package com.quizdeck.analysis;

import com.quizdeck.Application.QuizDeckApplication;
import com.quizdeck.analysis.exceptions.AnalysisException;
import com.quizdeck.analysis.inputs.*;
import com.quizdeck.analysis.outputs.QuizAnalysisData;
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
    @Test
    public void dummyTest() throws AnalysisException {
        LinkedList<Question> questions = new LinkedList<>();
        questions.add(new MockQuestion(1, new MockSelection('0')));
        questions.add(new MockQuestion(2, new MockSelection('1')));


        MockMember steve = new MockMember();
        LinkedList<Response> responses = new LinkedList<>();
        for(int i = 0; i < 50; i++)
            responses.add(new MockResponse(steve, new MockSelection(Integer.toString(i).charAt(0)), questions.get(0), i));

        QuizAnalysisFactory factory = new QuizAnalysisFactory();
        factory.setResponses(responses);
        factory.setQuestions(questions);
        factory.setQuizID("Q1");
        factory.setDeckID("D1");
        factory.setOwner(steve);
        StaticAnalysis analysis = (StaticAnalysis) factory.getAnalysisUsing(QuizAlgorithm.ACCURACY);
        analysis.performAnalysis();
        QuizAnalysisData result = (QuizAnalysisData) analysis.getResults();
    }
}

