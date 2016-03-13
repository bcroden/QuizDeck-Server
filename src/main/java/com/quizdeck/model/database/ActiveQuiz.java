package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Object for storing active quiz info in redis
 *
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
public class ActiveQuiz implements Serializable {

    private Date start;
    private Date stop;

    private boolean active;

    public ActiveQuiz(Date start, boolean active){
        this.start = start;
        this.active = active;
    }

    public ActiveQuiz(){}

}
