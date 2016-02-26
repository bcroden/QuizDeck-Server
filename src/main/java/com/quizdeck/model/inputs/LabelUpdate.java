package com.quizdeck.model.inputs;

import java.util.List;

/**
 * Created by Cade on 2/23/2016.
 *
 * input object to add new labels to a quiz
 */
public class LabelUpdate {

    private String userName;
    private String quizTitle;

    private List<String> labels;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }
}
