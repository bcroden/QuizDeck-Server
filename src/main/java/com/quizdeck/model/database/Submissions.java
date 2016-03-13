package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Guess;
import com.quizdeck.analysis.inputs.Response;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Cade on 3/12/2016.
 */

@Getter
@Setter
public abstract class Submissions implements Response, Serializable{

    public List<Guess> guesses;

    public Questions question;

}
