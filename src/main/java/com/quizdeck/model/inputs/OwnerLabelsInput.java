package com.quizdeck.model.inputs;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Cade on 2/27/2016.
 */

@Getter
@Setter
public class OwnerLabelsInput {
    private String owner;
    private List<String> labels;
}
