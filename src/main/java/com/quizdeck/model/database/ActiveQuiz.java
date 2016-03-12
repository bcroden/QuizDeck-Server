package com.quizdeck.model.database;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Object for storing active quiz info in redis
 *
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
public class ActiveQuiz {

    private Date start;
    private Date stop;

    private boolean active;

}
