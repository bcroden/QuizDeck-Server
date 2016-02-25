package com.quizdeck.model.database;

import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */

@Document
public class CompleteQuiz {

    private String quizId;

    private Quiz quiz;

    private Date start;
    private Date stop;

    private String title;
    private String owner;

    private List<submission> submissions;

    @PersistenceConstructor
    public CompleteQuiz(Quiz quiz, Date start, Date stop, String title, String owner, List<submission> submissions) {
        this.quiz = quiz;
        this.quizId = quiz.getId() + new Date().toString();
        this.start = start;
        this.stop = stop;
        this.title = title;
        this.owner = owner;
        this.submissions = submissions;
    }

    @Deprecated
    public CompleteQuiz(){};

    public String getQuizId() {
        return quizId;
    }

    public void setQuizId(String quizId) {
        this.quizId = quizId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
