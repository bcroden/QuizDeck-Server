package com.quizdeck.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by Cade on 5/2/2016.
 */

@Getter
@Setter
@Service
public class RedisShortCodes {

    @Autowired
    public RedisTemplate redisTemplate;

    private String ClassKey = "QuizShortCodes";

    @Resource(name="redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    public boolean takenShortCode(String shortCode){
        Set<String> keys = hashOperations.entries(ClassKey).keySet();
        if(keys != null && keys.size() > 0) {
            return keys.contains(shortCode);
        }
        return false;
    }

    public void addEntry(String shortCode, String quizId){
        hashOperations.put(ClassKey, shortCode, quizId);
    }

    public String getEntry(String shortCode){
        return hashOperations.get(ClassKey, shortCode);
    }

    public void removeEntry(String shortCode){
        hashOperations.delete(ClassKey, shortCode);
    }
}
