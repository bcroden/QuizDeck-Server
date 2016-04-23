package com.quizdeck.controllers;

import com.quizdeck.exceptions.ForbiddenAccessException;
import com.quizdeck.exceptions.InvalidJsonException;
import com.quizdeck.exceptions.UserDoesNotExistException;
import com.quizdeck.model.database.User;
import com.quizdeck.model.responses.UserSearchOutput;
import com.quizdeck.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by Cade on 3/11/2016.
 */
@RestController
@RequestMapping("/rest/secure/user")
public class UserRequestController {

    private Logger log = LoggerFactory.getLogger(UserRequestController.class);

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
    public ResponseEntity<String> deleteUser(@ModelAttribute("claims") Claims claims, @PathVariable String userId)throws ForbiddenAccessException{
        User temp = userRepository.findByUserName(claims.get("user").toString());
        if(temp.getId() != userId) {
            throw new ForbiddenAccessException();
        }
        userRepository.delete(userId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/subcribe/{userName}", method=RequestMethod.GET)
    public ResponseEntity<String> subscribe(@ModelAttribute("claims") Claims claims, @PathVariable String userName)throws UserDoesNotExistException{
        User you = userRepository.findByUserName(claims.get("user").toString());
        User them = userRepository.findByUserName(userName);
        if(you != null && them != null) {
            ArrayList<String> subscribed = new ArrayList<>();
            subscribed.addAll(you.getSubscriptions());
            subscribed.add(userName);

            you.setSubscriptions(subscribed);

            userRepository.save(you);

            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else{
            throw new UserDoesNotExistException();
        }
    }

    @RequestMapping(value="/findUser/{userName}", method = RequestMethod.GET)
    public ArrayList<UserSearchOutput> findUser(@PathVariable String userName){

        ArrayList<User> users = userRepository.findByUserNameLike(userName);
        ArrayList<UserSearchOutput> out = new ArrayList<>();
        if(users == null || users.size() == 0) {
            log.info("query returned nothing");
        }
        else{
            for (User user : users) {
                out.add(new UserSearchOutput(user.getUserName()));
            }
        }

        return out;
    }

}
