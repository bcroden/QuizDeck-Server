package com.quizdeck.model.inputs;


import com.quizdeck.model.database.Questions;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewQuizInput {

    private String owner;
    private String title;


    private List<Questions> questions;

    private List<String> labels;

    private List<String> categories;

    }