package com.quizdeck.model.database;

/**
 * Created by Cade on 2/16/2016.
 */
public class Answers {

    private String content;
    private boolean correct;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}
