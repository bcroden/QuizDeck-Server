package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.inputs.CreateAccountInput;
import com.quizdeck.model.inputs.LoginInput;
import com.quizdeck.model.responses.AuthTokenResponse;
import com.quizdeck.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Brandon on 2/10/2016.
 */
@RestController
@RequestMapping("/rest/nonsecure/")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authService;

    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public AuthTokenResponse createAccount(@Valid @RequestBody CreateAccountInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        // TODO: Check if user already exists

        // TODO: Make new user in database

        // Return new token when new user is made
        String token = authService.buildToken(input.getUsername(), "User");
        return new AuthTokenResponse(token);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthTokenResponse login(@Valid @RequestBody LoginInput input, BindingResult result) throws InvalidJsonException {
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        // TODO: Check if input is valid

        // Return new token if login credentials are valid
        String token = authService.buildToken(input.getUsername(), "User");
        return new AuthTokenResponse(token);
    }
}
