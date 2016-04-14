package com.quizdeck.controllers;

import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.model.database.User;
import com.quizdeck.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> editUser(@Valid @RequestBody User input, BindingResult result) throws InvalidJsonException{
        if(result.hasErrors()){
            throw new InvalidJsonException();
        }

        userRepository.save(input);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/deleteUser", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable String userId){
        userRepository.delete(userId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }


    @RequestMapping(value="/findUser/{userName}", method = RequestMethod.GET)
    public User findUser(@PathVariable String userName){
        return userRepository.findByUserName(userName);
    }

}
