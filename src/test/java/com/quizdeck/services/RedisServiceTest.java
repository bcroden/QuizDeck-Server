package com.quizdeck.services;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.model.database.Questions;
import com.quizdeck.model.database.submission;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * Created by Cade on 3/10/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class RedisServiceTest {

    @Autowired
    RedisSubmissions redisSubmissions;

    @Test
    public void redisInsertSubmission(){
        submission sub = new submission();

        sub.setUserName("User2");
        sub.setGuesses(new ArrayList<Guess>());
        Questions q = new Questions();
        q.setQuestion("Whaaaaaaaaaaaaaaaaaaaaaaaaaat?");
        sub.setQuestion(q);

        redisSubmissions.addSubmissionLink("quizID", sub);

        assertThat(((submission)redisSubmissions.getAllSubmissions("quizID").get(0)).getUserName(), is(equalTo("User2")));

        assertThat(redisSubmissions.getFirstSubmission("quizID").getUserName(), is(equalTo("User2")));
    }
}
