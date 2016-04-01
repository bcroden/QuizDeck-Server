package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Cade on 2/16/2016.
 */

@Getter
@Setter
@Document
public class CompleteQuiz {

    @Deprecated
    private String id;

    private String quizId;

    private Quiz quiz;

    private Date start;
    private Date stop;

    private String title;
    private String owner;

    private List<? extends Submissions> submissions;

    //@Deprecated Note: Alex removed depreciation to help query completed quizzes based on labels
    private List<String> labels; //labels belong to the quiz object, not completed quizzes

    @PersistenceConstructor
    public CompleteQuiz(Quiz quiz, Date start, Date stop, String title, String owner, List<? extends Submissions> submissions) {
        this.quiz = quiz;
        this.quizId = quiz.getId() + new Date().toString();
        this.start = start;
        this.stop = stop;
        this.title = title;
        this.owner = owner;
        this.submissions = submissions;
        this.labels = new LinkedList<>(quiz.getLabels());
    }

    @Deprecated
    public CompleteQuiz(){}

}
