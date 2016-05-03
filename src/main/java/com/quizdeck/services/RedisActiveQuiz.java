package com.quizdeck.services;

import com.quizdeck.model.database.ActiveQuiz;
import com.quizdeck.model.database.User;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    private Logger log = LoggerFactory.getLogger(RedisActiveQuiz.class);

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
        log.info("Looking for quizzes for: " + user.getUserName());
        Map<String, ActiveQuiz> quizzes = hashOperations.entries(ClassKey);
        for(Map.Entry<String, ActiveQuiz> entry : quizzes.entrySet()){
            if(user.getSubscriptions() != null && user.getSubscriptions().size() > 0) {
                if (user.getSubscriptions().contains(entry.getValue().getOwner()) && entry.getValue().isPubliclyAvailable()) {
                    activeQuizzes.add(entry.getValue());
                }
            }
        }

        return activeQuizzes;
    }

    public long getKeySize(){
        return hashOperations.size(ClassKey);
    }

    public void updateEntry(String quizId, ActiveQuiz quiz) {
        quiz.setStart(hashOperations.get(ClassKey, prefix+quizId).getStart());
        hashOperations.put(ClassKey, prefix+quizId, quiz);
    }

    public void deleteAll(){
        Set<String> keys = hashOperations.keys(ClassKey);
        for(String key : keys){
            hashOperations.delete(ClassKey, key);
        }
    }

}
