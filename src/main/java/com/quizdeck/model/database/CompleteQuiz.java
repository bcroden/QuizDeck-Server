package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Response;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
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
}
