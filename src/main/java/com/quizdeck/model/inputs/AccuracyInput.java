package com.quizdeck.model.inputs;

/**
 * Created by Cade on 2/17/2016.
 */
public class AccuracyInput {

    private String id;

    @Deprecated
    private String owner;
    @Deprecated
    private String title;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
