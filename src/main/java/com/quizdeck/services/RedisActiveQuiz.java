package com.quizdeck.services;

import com.quizdeck.model.database.ActiveQuiz;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
@Service
public class RedisActiveQuiz {

    public RedisTemplate<String, ActiveQuiz> redisQuizTemplate;

    @Resource(name="redisQuizTemplate")
    private ListOperations<String, ActiveQuiz> listOps;

    public void addLink(String quizId, ActiveQuiz quiz){
        listOps.leftPush(quizId, quiz);
    }

    public ActiveQuiz getFirst(String quizId){return listOps.leftPop(quizId);}

    public void updateLink(String quizId, ActiveQuiz quiz) {
        quiz.setStart(listOps.leftPop(quizId).getStart());
        listOps.leftPush("quizId", quiz);
    }

}
