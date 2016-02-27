package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */
@Getter
@Setter
public class submission implements Response {

    private String userName;

    private List<Guess> choosenAnswers;

    private Questions question;

    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public List<Guess> getGuesses() {
        return choosenAnswers;
    }

    @Override
    public Question getQuestion() {
        return question;
    }
}
