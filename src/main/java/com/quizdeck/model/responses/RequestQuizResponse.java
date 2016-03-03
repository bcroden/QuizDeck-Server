package com.quizdeck.model.responses;

import com.quizdeck.model.database.Quiz;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 2/27/2016.
 */

@Getter
@Setter
public class RequestQuizResponse {

    private List<Quiz> quizzes;

}
