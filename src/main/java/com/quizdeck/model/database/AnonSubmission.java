package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Datatype for submissions without usernames
 * Limits analysis based on user name
 * Adds seemingly desired feature
 * Allows for analysis of submissions as a whole still
 *
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
public class AnonSubmission extends Submissions {

    public AnonSubmission(List<Guess> guesses, Questions question) {
        this.question = question;
        this.guesses = guesses;
    }

    @Override
    public String getUserName() {
        return "anonymous";
    }

    @Override
    public List<Guess> getGuesses() {
        return this.guesses;
    }
}

