package com.quizdeck.model.database;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Storage object for a quiz that has not been active yet
 *
 * Created by Cade on 2/14/2016.
 */
@Document
public class Quiz {

    @Id
    private String id;

    private String owner;
    private String title;

    private List<Questions> questions;

    private List<String> labels;

    @PersistenceConstructor
    public Quiz(String owner, String title, List<Questions> questions, List<String> labels) {
        this.owner = owner;
        this.title = title;
        this.questions = questions;
        this.labels = labels;
    }

    public void setOwner(String owner) {this.owner = owner;}

    public String getOwner() {
        return owner;
    }

    public String getId() {
        return id;
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
