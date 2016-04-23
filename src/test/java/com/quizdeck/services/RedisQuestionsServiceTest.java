package com.quizdeck.services;

import com.quizdeck.QuizDeckApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;


import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * Created by Cade on 4/23/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class RedisQuestionsServiceTest {

    @Autowired
    RedisQuestion redisQuestion;

    @Test
    public void redisQuestionTest(){

        redisQuestion.addEntry("quizId", 1);

        redisQuestion.updateEntry("quizId", 2);

        assertThat(redisQuestion.getEntry("quizId"), is(equalTo(2)));

        redisQuestion.updateEntry("quizId", 1);

        assertThat(redisQuestion.getEntry("quizId"), is(equalTo(1)));

        redisQuestion.removeEntry("quizId");

        assertThat(redisQuestion.getEntry("quizId"), is(equalTo(null)));

    }

}
