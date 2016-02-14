package com.quizdeck.model.inputs;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the information that a client sends to the server to log in.
 *
 * Created by Brandon on 2/12/2016.
 */
public class LoginInput {
    @NotNull
    private String username;

    @NotNull
    private String password;

    public LoginInput() {
    }

    public LoginInput(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
