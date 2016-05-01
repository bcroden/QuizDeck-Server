package com.quizdeck.services;

import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.database.submission;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cade on 3/8/2016.
 */
@Getter
@Setter
@Service
public class RedisSubmissions {

    @Autowired
    private RedisTemplate redisTemplate;

    private String prefix = "RS";

    @Resource(name="redisTemplate")
    private ListOperations<String, Submissions> listOps;

    public void addSubmissionLink(String quizId, submission sub){
        listOps.leftPush(prefix+quizId, sub);
    }

    public long getSize(String quizId) {return listOps.size(prefix+quizId);}

    public Submissions getSubmission(String quizId, long index) {
       return listOps.index(prefix+quizId, index);
    }

    public void removeIndex(String quizId, long index, Submissions sub){
        listOps.remove(prefix+quizId, index, sub);
    }

    public submission getFirstSubmission(String quizId) {return (submission) listOps.leftPop(prefix+quizId);}

    public List<? extends Submissions> getAllSubmissionsAndRemove(String quizId){
        List<Submissions> subs = new ArrayList<>();
        for(int i = 0; i < listOps.size(prefix+quizId); i++){
            subs.add(listOps.leftPop(prefix+quizId));
        }
        return subs;
    }

    public List<? extends Submissions> getAllSubmissions(String quizId){
        return listOps.range(prefix+quizId, 0, listOps.size(prefix+quizId));
    }
}
