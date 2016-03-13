package com.quizdeck.services;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.model.database.ActiveQuiz;
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
 * Created by Cade on 3/12/2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class RedisActiveQuizTest {

    @Autowired
    RedisActiveQuiz redisActiveQuiz;

    @Test
    public void activeQuizInfo(){
        ActiveQuiz activeQuiz = new ActiveQuiz();
        activeQuiz.setActive(true);

        redisActiveQuiz.addEntry("quizId", activeQuiz);

        assertThat(redisActiveQuiz.getEntry("quizId").isActive(), is(equalTo(true)));

        redisActiveQuiz.removeEntry("quizId");

        assertThat(redisActiveQuiz.getEntry("quizId"), is(equalTo(null)));
    }

}
