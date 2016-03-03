package com.quizdeck.model.inputs;

import com.quizdeck.model.database.Quiz;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 2/27/2016.
 */
@Getter
@Setter
public class QuizEditInput {

    private Quiz editedQuiz;

}
