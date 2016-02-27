package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * Storage object for a quiz that has not been active yet
 *
 * Created by Cade on 2/14/2016.
 */
@Getter
@Setter
@Document
public class Quiz {

    @Id
    private String id;

    private String owner;
    private String title;

    private List<Questions> questions;

    private List<String> labels;

    @PersistenceConstructor
    public Quiz(String owner, String title, List<Questions> questions, List<String> labels) {
        this.owner = owner;
        this.title = title;
        this.questions = questions;
        this.labels = labels;
    }

    @Deprecated
    public Quiz(){};

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

}
