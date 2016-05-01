package com.quizdeck.services;

import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
@Service
public class RedisActiveQuiz {

    @Autowired
    public RedisTemplate redisTemplate;

    private String ClassKey ="ActiveQuiz";
    private String prefix = "AQ";
    
    @Resource(name="redisTemplate")
    private HashOperations<String, String,ActiveQuiz> hashOperations;

    public void addEntry(String quizId, ActiveQuiz quiz){
        hashOperations.put(ClassKey, prefix+quizId, quiz);
    }

    public ActiveQuiz getEntry(String quizId){
        ActiveQuiz aq = hashOperations.get(ClassKey, prefix+quizId);
        return aq;
    }

    public void removeEntry(String quizId){
        hashOperations.delete(ClassKey, prefix+quizId);
    }

    public List<ActiveQuiz> getAllActiveQuizzes(User user){
        List<ActiveQuiz> activeQuizzes = new ArrayList<>();

        return activeQuizzes;
    }

    public long getKeySize(){
        return hashOperations.size(ClassKey);
    }

    public void updateEntry(String quizId, ActiveQuiz quiz) {
        quiz.setStart(hashOperations.get(ClassKey, prefix+quizId).getStart());
        hashOperations.put(ClassKey, prefix+quizId, quiz);
    }

}
