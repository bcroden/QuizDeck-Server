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

    private String keyIdentifier = "S";

    @Resource(name="redisTemplate")
    private ListOperations<String, Submissions> listOps;

    public void addSubmissionLink(String quizId, submission sub){
        listOps.leftPush(quizId+keyIdentifier, sub);
    }

    public void addAnonSubmissionLink(String quizId, AnonSubmission sub){
        listOps.leftPush(quizId+keyIdentifier, sub);
    }

    public long getSize(String quizId) {return listOps.size(quizId+keyIdentifier);}

    public Submissions getSubmission(String quizId, long index) {
       return listOps.index(quizId+keyIdentifier, index);
    }

    public Submissions getAnonSubmission(String quizId, long index) {
        return listOps.index(quizId+keyIdentifier, index);
    }

    public void removeIndex(String quizId, long index, Submissions sub){
        listOps.remove(quizId+keyIdentifier, index, sub);
    }

    public submission getFirstSubmission(String quizId) {return (submission) listOps.leftPop(quizId+keyIdentifier);}

    public AnonSubmission getFirstAnonSubmission(String quizId) {return (AnonSubmission) listOps.leftPop(quizId+keyIdentifier);}

    public List<? extends Submissions> getAllSubmissionsAndRemove(String quizId){
        List<Submissions> subs = new ArrayList<>();
        for(int i = 0; i < listOps.size(quizId+keyIdentifier); i++){
            subs.add(listOps.leftPop(quizId+keyIdentifier));
        }
        return subs;
    }

    public List<? extends Submissions> getAllSubmissions(String quizId){
        return listOps.range(quizId+keyIdentifier, 0, listOps.size(quizId+keyIdentifier));
    }
}
