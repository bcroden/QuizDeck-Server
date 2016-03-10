package com.quizdeck.services;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.model.database.Answers;
import com.quizdeck.model.database.Questions;
import com.quizdeck.model.database.submission;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cade on 3/8/2016.
 */
public class RedisSubmissionTest {

    @Autowired
    private RedisSubmissions redisSubmissions;

    @Test
    @PostConstruct
    public void insertSubmission(){

        Questions q = new Questions();
        q.setQuestion("this");
        q.setCorrectAnswerID("a");
        List<Answers> temp = new ArrayList<>();
        temp.add(new Answers());
        q.setAnswers(temp);
        submission submission = new submission();
        submission.setUserName("user");
        submission.setQuestion(q);
        submission.setChoosenAnswers(new ArrayList<Guess>());

        //either of these will npe
        //I don't know why redisSubmissions is null
        redisSubmissions.getRedisTemplate().opsForList().leftPush("quizID", submission);
        redisSubmissions.addLink("quizID", submission);
    }

}
