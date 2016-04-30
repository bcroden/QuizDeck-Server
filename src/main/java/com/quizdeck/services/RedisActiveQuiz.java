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
    
    private String keyIdentifier = "AQ";

    @Resource(name="redisTemplate")
    private ValueOperations<String, ActiveQuiz> valueOperations;

    public void addEntry(String quizId, ActiveQuiz quiz){
        valueOperations.set(quizId+keyIdentifier, quiz);
    }

    public ActiveQuiz getEntry(String quizId){
        ActiveQuiz aq = valueOperations.get(quizId+keyIdentifier); //returns an integer somehow
        return aq;
    }

    public void removeEntry(String quizId){
        valueOperations.getOperations().delete(quizId+keyIdentifier);}

    public void updateEntry(String quizId, ActiveQuiz quiz) {
        quiz.setStart(valueOperations.get(quizId+keyIdentifier).getStart());
        valueOperations.getAndSet(quizId+keyIdentifier, quiz);
    }

}
