package com.quizdeck.model.database;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.quizdeck.analysis.inputs.Question;
import com.quizdeck.analysis.inputs.Selection;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Questions extends Question {

    private String question;
    private String questionFormat;

    private String correctAnswerID;

    private List<Answers> answers;

    private int questionNum;

    @JsonIgnore
    public Selection getCorrectAnswer() {
        return answers.stream().filter((answer) -> answer.getId().equals(correctAnswerID)).findFirst().get();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Questions questions = (Questions) o;

        if (questionNum != questions.questionNum) return false;
        return question.equals(questions.question);

    }

    @Override
    public int hashCode() {
        int result = question.hashCode();
        result = 31 * result + questionNum;
        return result;
    }

    @Override
    public int compareTo(Question o) {
        if(questionNum < o.getQuestionNum())
            return -1;
        if(questionNum > o.getQuestionNum())
            return 1;
        return 0;
    }
}
