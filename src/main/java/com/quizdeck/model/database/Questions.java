package com.quizdeck.model.database;

import java.util.List;

public class Questions {

    private String question;
    private String questionFormat;

    private List<String> answers;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionFormat() {
        return questionFormat;
    }

    public void setQuestionFormat(String questionFormat) {
        this.questionFormat = questionFormat;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }
}
