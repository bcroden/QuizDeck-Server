package com.quizdeck.model.responses;

import com.quizdeck.model.database.Answers;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 5/2/2016.
 */
@Getter
@Setter
public class QuestionResponse {

    private String question;
    private String questionFormat;

    private List<Answers> answers;

    private int numAnswers;

    public QuestionResponse(String question, String questionFormat, List<Answers> answers) {
        this.question = question;
        this.questionFormat = questionFormat;
        this.answers = answers;
        this.numAnswers = answers.size();
    }
}
