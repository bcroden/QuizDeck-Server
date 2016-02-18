package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */
public class CompleteQuiz implements Response{

    @Id
    private String id;

    private Quiz quiz;

    private Date start;
    private Date stop;

    private List<submission> submissions;


    @Override
    public List<String> getParticipant() {
        List<String> participants = new ArrayList<>();

        for(submission sub : submissions){
            participants.add(sub.getUserName());
        }

        return participants;
    }

    @Override
    public List<Guess> getGuess() {
        List<Guess> answers = new ArrayList<>();

        for(submission sub : submissions){
            answers.addAll(sub.getChoosenAnswers());
        }

        return answers;
    }

    @Override
    public Question getQuestion() {
        return null;
    }
}
