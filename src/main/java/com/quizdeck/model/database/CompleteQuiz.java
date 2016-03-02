package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */

@Getter
@Setter
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

}
