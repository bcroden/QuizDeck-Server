package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;

import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */
public class submission implements Response {

    private String userName;

    private List<Guess> choosenAnswers;

    private Questions question;

    @Override
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Guess> getChoosenAnswers() {
        return choosenAnswers;
    }

    public void setChoosenAnswers(List<Guess> choosenAnswers) {
        this.choosenAnswers = choosenAnswers;
    }

    @Override
    public List<Guess> getGuesses() {
        return choosenAnswers;
    }

    @Override
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Questions question) {
        this.question = question;
    }
}
