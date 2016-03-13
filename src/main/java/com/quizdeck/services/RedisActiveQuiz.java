package com.quizdeck.services;

import com.quizdeck.model.database.ActiveQuiz;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
@Service
public class RedisActiveQuiz {

    public RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ValueOperations<String, ActiveQuiz> valueOps;

    public void addEntry(String quizId, ActiveQuiz quiz){valueOps.set(quizId, quiz);}

    public ActiveQuiz getEntry(String quizId){return valueOps.get(quizId);}

    public void removeEntry(String quizId){valueOps.getOperations().delete(quizId);}

    public void updateEntry(String quizId, ActiveQuiz quiz) {
        quiz.setStart(valueOps.get(quizId).getStart());
        valueOps.getAndSet(quizId, quiz);
    }

}
