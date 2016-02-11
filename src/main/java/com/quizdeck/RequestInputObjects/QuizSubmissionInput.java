package com.quizdeck.RequestInputObjects;


import com.quizdeck.QuizObjects.Questions;

import java.util.Date;
import java.util.List;

public class QuizSubmissionInput{

    private String owner;
    private String title;
    private String quizId;
    private Date quizDate;

    //should be a list of usernames of uIDs
    private List<String> quizTakers;

    private List<Questions> questions;


    public QuizSubmissionInput(){
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

    public Date getQuizDate() {
        return quizDate;
    }

    public void setQuizDate(Date quizDate) {
        this.quizDate = quizDate;
    }

    public List<String> getQuizTakers() {
        return quizTakers;
    }

    public void setQuizTakers(List<String> quizTakers) {
        this.quizTakers = quizTakers;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }
}