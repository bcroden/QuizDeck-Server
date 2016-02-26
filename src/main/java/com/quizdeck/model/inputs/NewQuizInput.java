package com.quizdeck.model.inputs;


import com.quizdeck.model.database.Questions;

import java.util.List;

public class NewQuizInput {

    private String owner;
    private String title;
    private String quizId;

    private List<Questions> questions;

    private List<String> labels;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

    public List<String> getLabels() {return labels;}

    public void setLabels(List<String> labels) {this.labels = labels;}
}