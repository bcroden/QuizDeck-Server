package com.quizdeck.model.inputs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 2/23/2016.
 *
 * input object to add new labels to a quiz
 */
@Getter
@Setter
public class LabelUpdate {

    private String userName;
    private String quizTitle;

    private List<String> labels;


}
