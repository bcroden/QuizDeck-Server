package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Storage object for a quiz that has not been active yet
 *
 * Created by Cade on 2/14/2016.
 *
 * @param publicAvailable will be used to determine if a user can see another users quiz.
 */

@Getter
@Setter
@Document
public class Quiz {

    @Id
    private String id;

    @NotNull
    private String owner;

    @NotNull
    private String title;

    private List<Questions> questions;

    private List<String> labels;

    @NotNull
    private List<String> categories;

    private boolean publicAvailable;

    public Quiz(String owner, String title, List<Questions> questions, List<String> labels, List<String> categories, boolean publicAvailable) {
        this.owner = owner;
        this.title = title;
        this.questions = questions;
        this.labels = labels;
        this.categories = categories;
        this.publicAvailable = publicAvailable;
    }

    @Deprecated
    public Quiz(){
    }

    @Deprecated
    public void setId(String id) {
        this.id = id;
    }

}
