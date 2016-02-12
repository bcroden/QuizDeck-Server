package com.quizdeck.controllers;

import com.quizdeck.model.inputs.CreateAccountInput;
import com.quizdeck.model.inputs.LoginInput;
import com.quizdeck.model.responses.AuthTokenResponse;
import com.quizdeck.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Brandon on 2/10/2016.
 */
@RestController
@RequestMapping("/rest/nonsecure/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public AuthTokenResponse createAccount(CreateAccountInput input) {
        // TODO: Check if user already exists

        // TODO: Make new user in database

        // Return new token when new user is made
        String token = authService.buildToken(input.getUsername(), "User");
        return new AuthTokenResponse(token);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(LoginInput input) {
        // TODO: Check if input is valid

        // Return new token if login credentials are valid
        return authService.buildToken(input.getUsername(), "User");
    }
}
