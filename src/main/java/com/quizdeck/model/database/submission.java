package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;

import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */
public class submission {

    private String userName;

    private List<Guess> choosenAnswers;

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
}
