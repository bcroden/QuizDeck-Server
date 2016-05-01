package com.quizdeck.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Cade on 4/22/2016.
 */
@Getter
@Setter
@Service
public class RedisQuestion {

    @Autowired
    public RedisTemplate redisTemplate;

    private String prefix = "RQ";

    @Resource(name="redisTemplate")
    private ValueOperations<String, Integer> valOps;

    public void addEntry(String quizId, int questionNum){valOps.set(prefix+quizId, questionNum);}

    public Integer getEntry(String quizId){return valOps.get(prefix+quizId);}

    public void updateEntry(String quizId, int questionNum){valOps.set(prefix+quizId, questionNum);}

    public void removeEntry(String quizId){valOps.getOperations().delete(prefix+quizId);}

}
