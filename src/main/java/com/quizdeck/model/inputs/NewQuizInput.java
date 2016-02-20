package com.quizdeck.model.inputs;


import com.quizdeck.model.database.Questions;

import java.util.List;

public class NewQuizInput {

    private String owner;
    private String title;
    private String quizId;

    private List<Questions> questions;

    public String getOwner() {
        return owner;
    }


    //don't know what information is needed to recreate the page from json


    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
}