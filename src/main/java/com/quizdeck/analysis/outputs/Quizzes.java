package com.quizdeck.analysis.outputs;

import com.quizdeck.model.database.Quiz;

import java.util.List;

/**
 * Created by Cade on 2/23/2016.
 *
 * response to a request for a users quizzes
 */
public class Quizzes {

    private List<Quiz> quizzes;

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }
}
