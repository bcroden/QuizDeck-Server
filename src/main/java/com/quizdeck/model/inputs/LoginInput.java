package com.quizdeck.model.inputs;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the information that a client sends to the server to log in.
 *
 * Created by Brandon on 2/12/2016.
 */
public class LoginInput {
    @NotNull
    private String userName;

    @NotNull
    private String password;

    public LoginInput() {
    }

    public LoginInput(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
