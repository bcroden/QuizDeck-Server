package com.quizdeck.RequestInputObjects;

import com.quizdeck.QuizObjects.Questions;
import com.quizdeck.QuizObjects.QuizTakers;

import java.util.Date;
import java.util.List;

public class QuizSubmissionInput{

    private String owner;
    private String title;
    private String quizId;
    private Date quizDate;

    private List<QuizTakers> quizTakers;

    private List<Questions> questions;


    public QuizSubmissionInput(){
    }




}