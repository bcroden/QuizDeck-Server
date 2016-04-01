package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.inputs.UserDeleteInput;
import com.quizdeck.model.inputs.UserEditInput;
import com.quizdeck.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by Cade on 3/11/2016.
 */
@RestController
@RequestMapping("/rest/secure/user")
public class UserRequestController {

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value="/edit", method= RequestMethod.PUT)
    public ResponseEntity<String> editUser(@Valid @RequestBody UserEditInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        userRepository.save(input.getEditedUser());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/deleteUser", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@Valid @RequestBody UserDeleteInput input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        userRepository.delete(input.getId());
        return new ResponseEntity<String>(HttpStatus.OK);
    }

}
