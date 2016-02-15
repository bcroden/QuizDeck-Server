package com.quizdeck.model.inputs;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * DTO containing the information that a client sends to the server to create a new account.
 *
 * Created by Brandon on 2/12/2016.
 */
public class CreateAccountInput {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private Date signUp;

    public CreateAccountInput() {
    }

    public CreateAccountInput(String username, String password, String email, Date signUp) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.signUp = signUp;
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

    public Date getSignUp() {
        return signUp;
    }

    public void setSignUp(Date signUp) {
        this.signUp = signUp;
    }
}
