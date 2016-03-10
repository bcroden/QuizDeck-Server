package com.quizdeck.services;

import com.quizdeck.model.database.submission;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by Cade on 3/10/2016.
 */

public class RedisServiceTest {

    @Autowired
    RedisSubmission redisSubmission;

    @Test
    public void insertSubmission(){

        submission sub = new submission();


        redisSubmission.getRedisTemplate().opsForList().leftPush("quizID", sub);
    }

}
