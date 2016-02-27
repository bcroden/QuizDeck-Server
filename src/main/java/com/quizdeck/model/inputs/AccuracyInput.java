package com.quizdeck.model.inputs;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Cade on 2/17/2016.
 */

@Getter
@Setter
public class AccuracyInput {

    private String id;

    @Deprecated
    private String owner;
    @Deprecated
    private String title;

}
