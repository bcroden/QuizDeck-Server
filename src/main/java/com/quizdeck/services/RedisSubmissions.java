package com.quizdeck.services;

import com.quizdeck.model.database.AnonSubmission;
import com.quizdeck.model.database.Submissions;
import com.quizdeck.model.database.submission;
import lombok.Getter;
import lombok.Setter;
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

    private RedisTemplate redisTemplate;

    @Resource(name="redisTemplate")
    private ListOperations<String, Submissions> listOps;

    public void addSubmissionLink(String quizId, submission sub){
        listOps.leftPush(quizId, sub);
    }

    public void addAnonSubmissionLink(String quizId, AnonSubmission sub){
        listOps.leftPush(quizId, sub);
    }

    public long getSize(String quizId) {return listOps.size(quizId);}

    public Submissions getSubmission(String quizId, long index) {
       return listOps.index(quizId, index);
    }

    public void removeIndex(String quizId, long index, Submissions sub){
        listOps.remove(quizId, index, sub);
    }

    public submission getFirstSubmission(String quizID) {return (submission) listOps.leftPop(quizID);}

    public AnonSubmission getFirstAnonSubmission(String quizID) {return (AnonSubmission) listOps.leftPop(quizID);}

    public List<? extends Submissions> getAllSubmissionsAndRemove(String quizID){
        List<Submissions> subs = new ArrayList<>();
        for(int i = 0; i < listOps.size(quizID); i++){
            subs.add(listOps.leftPop(quizID));
        }
        return subs;
    }

    public List<? extends Submissions> getAllSubmissions(String quizID){
        return listOps.range(quizID, 0, (listOps.size(quizID)-1));
    }
}
