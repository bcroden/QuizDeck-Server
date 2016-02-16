package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.exceptions.UserExistsException;
import com.quizdeck.exceptions.UsernamePassException;
import com.quizdeck.model.database.User;
import com.quizdeck.model.inputs.CreateAccountInput;
import com.quizdeck.model.inputs.LoginInput;
import com.quizdeck.model.responses.AuthTokenResponse;
import com.quizdeck.repositories.UserRepository;
import com.quizdeck.services.AuthenticationService;
import com.quizdeck.services.PassEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

/**
 * This controller manages account creation and user logins.
 * Users post information to these endpoints and receive an authentication token in return.
 * This token can be included as a header in future requests to secure endpoints and will confirm the users identity.
 *
 * Created by Brandon on 2/10/2016.
 */
@RestController
@RequestMapping("/rest/nonsecure/")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassEncryption encrypt;

    /**
     * The endpoint for account creation.
     *
     * @param input The JSON request
     * @param result The JSON binding results
     * @return A JSON object containing a token fields.
     * @throws InvalidJsonException If the JSON is malformed or missing important fields.
     */
    @RequestMapping(value = "/createAccount", method = RequestMethod.POST)
    public AuthTokenResponse createAccount(@Valid @RequestBody CreateAccountInput input, BindingResult result) throws InvalidJsonException, UserExistsException {
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        if(userRepository.findByUserName(input.getUsername()) != null){
            throw new UserExistsException();
        }
        else{
            ArrayList<byte[]> hashResult = encrypt.encryptAndSeed(input.getPassword());
            String storedPass = Base64.getEncoder().encodeToString(hashResult.get(0));
            String storedSalt = Base64.getEncoder().encodeToString(hashResult.get(1));
            userRepository.save(new User(input.getUsername(), storedPass, storedSalt,input.getEmail(), new Date()));
            //entered new user with a hash of pass, salted with a key provided by hashResult[1]
        }

        // Return new token when new user is made
        String token = authService.buildToken(input.getUsername(), "User");
        return new AuthTokenResponse(token);
    }

    /**
     * The endpoint for user logins.
     *
     * @param input The JSON request
     * @param result The JSON binding results
     * @return A JSON object containing a token fields.
     * @throws InvalidJsonException If the JSON is malformed or missing important fields.
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public AuthTokenResponse login(@Valid @RequestBody LoginInput input, BindingResult result) throws InvalidJsonException, UsernamePassException {
        if(result.hasErrors()) {
            throw new InvalidJsonException();
        }

        // TODO: Check if input is valid
        User currUser = currUser = userRepository.findByUserName(input.getUsername());

        if(currUser != null){
            byte[] salt = Base64.getDecoder().decode(currUser.getSaltSeed());

            if(encrypt.encryptUsingSaltSeed(input.getPassword().getBytes(), salt).equals(currUser.getHashedPassword())){
                //logic for logging in
                String token = authService.buildToken(input.getUsername(), "User");
                return new AuthTokenResponse(token);
            }
            else{
                throw new UsernamePassException();
            }
        }
        else{
            throw new UsernamePassException();
        }
    }
}
