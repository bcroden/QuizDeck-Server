package com.quizdeck.model.database;

import com.quizdeck.analysis.inputs.Selection;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * Created by Cade on 2/16/2016.
 */

@Getter
@Setter
public class Answers extends Selection implements Serializable {

    private String content;
    private String id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Answers answers = (Answers) o;

        return id.equals(answers.id);
    }

    @Override
    public int hashCode() {
        int result = content != null ? content.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }

}
