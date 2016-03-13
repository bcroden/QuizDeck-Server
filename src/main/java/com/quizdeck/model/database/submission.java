package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */
@Getter
@Setter
public class submission extends Submissions implements Response{

    private String userName;

    public submission(String username, List<Guess> guesses, Questions question) {
        this.userName = username;
        this.question = question;
        this.guesses = guesses;
    }

    public submission(){}

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public List<Guess> getGuesses() {
        return guesses;
    }

    @Override
    public Questions getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "submission{" +
                "userName='" + userName + '\'' +
                ", guesses=" + guesses +
                ", question=" + question +
                '}';
    }
}
