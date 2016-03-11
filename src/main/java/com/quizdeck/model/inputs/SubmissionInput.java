package com.quizdeck.model.inputs;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.model.database.Questions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 3/10/2016.
 */
@Getter
@Setter
public class SubmissionInput {
    private String quizID;

    private String userName;

    private List<Guess> choosenAnswers;

    private Questions question;
}
