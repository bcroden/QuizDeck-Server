package com.quizdeck.model.inputs;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * DTO containing the information that a client sends to the server to create a new account.
 *
 * Created by Brandon on 2/12/2016.
 */

@Getter
@Setter
public class CreateAccountInput {
    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String email;

    public CreateAccountInput() {
    }

    public CreateAccountInput(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
}
