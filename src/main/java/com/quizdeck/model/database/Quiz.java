package com.quizdeck.model.database;

import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * Storage object for a quiz that has not been active yet
 *
 * Created by Cade on 2/14/2016.
 */
public class Quiz {

    @Id
    private String id;

    private String owner;
    private String title;
    private String quizId;

    private List<Questions> questions;

    public Quiz(String id, String owner, String title, String quizId, List<Questions> questions) {
        this.id = id;
        this.owner = owner;
        this.title = title;
        this.quizId = quizId;
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
