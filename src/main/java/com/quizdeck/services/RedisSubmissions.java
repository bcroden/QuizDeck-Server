package com.quizdeck.services;

import com.quizdeck.model.database.submission;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Cade on 3/8/2016.
 */
@Getter
@Setter
@Service
public class RedisSubmissions {

    private RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ListOperations<String, submission> listOps;

    public void addLink(String quizId, submission sub){
        listOps.leftPush(quizId, sub);
    }

    public long getSize(String quizId) {return listOps.size(quizId);}

    public submission getFirst(String quizID) {return listOps.leftPop(quizID);}
}
