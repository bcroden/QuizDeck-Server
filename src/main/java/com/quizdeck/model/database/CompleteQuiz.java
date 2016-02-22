package com.quizdeck.model.database;

import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */
public class CompleteQuiz {

    @Id
    private String id;

    private Quiz quiz;

    private Date start;
    private Date stop;

    private String title;
    private String owner;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private List<submission> submissions;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public List<submission> getSubmissions() {
        return submissions;
    }

    public void setSubmissions(List<submission> submissions) {
        this.submissions = submissions;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
