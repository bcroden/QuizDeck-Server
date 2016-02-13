package com.quizdeck.model.inputs;

/**
 * Created by Brandon on 2/12/2016.
 */
public class CreateAccountInput {
    private String username;
    private String password;
    private String email;

    public CreateAccountInput() {
    }

    public CreateAccountInput(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
