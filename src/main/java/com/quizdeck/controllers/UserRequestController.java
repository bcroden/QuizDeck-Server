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
import java.util.List;

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

    @RequestMapping(value="/deleteUser/{userId}", method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@ModelAttribute("claims") Claims claims, @PathVariable String userId)throws ForbiddenAccessException{
        User temp = userRepository.findByUserName(claims.get("user").toString());
        if(claims.get("user").toString().equals(temp.getUserName()) || claims.get("role").equals("Admin")) {
            userRepository.delete(userId);
            return new ResponseEntity<String>(HttpStatus.OK);
        }
        throw new ForbiddenAccessException();
    }

    @RequestMapping(value="/subscribe/{userName}", method=RequestMethod.PUT)
    public ResponseEntity<String> subscribe(@ModelAttribute("claims") Claims claims, @PathVariable String userName)throws UserDoesNotExistException{
        User you = userRepository.findByUserName(claims.get("user").toString());
        User them = userRepository.findByUserName(userName);
        if(you != null && them != null) {
            ArrayList<String> subscribed = new ArrayList<>();
            if(you.getSubscriptions() != null && you.getSubscriptions().size() > 0) {
                subscribed.addAll(you.getSubscriptions());
            }
            if(!subscribed.contains(userName)) {
                subscribed.add(userName);
                you.setNumSubscribed(you.getNumSubscribed()+1);
            }

            you.setSubscriptions(subscribed);

            userRepository.save(you);

            return new ResponseEntity<String>(HttpStatus.OK);
        }
        else{
            throw new UserDoesNotExistException();
        }
    }

    @RequestMapping(value="/unSubscribe/{userName}", method=RequestMethod.PUT)
    public ResponseEntity<String> unSubscribe(@ModelAttribute("claims") Claims claims, @PathVariable String userName) throws UserDoesNotExistException{

        User you = userRepository.findByUserName(claims.get("user").toString());
        if(you == null){
            throw new UserDoesNotExistException();
        }

        if(you.getSubscriptions().contains(userName)){
            for(String user : you.getSubscriptions()){
                if(user.equals(userName)){
                    log.info("in if");
                    List<String> newSubsList = new ArrayList<>(you.getSubscriptions());
                    newSubsList.remove(userName);
                    log.info("Removed " + userName + " from new list");
                    you.setSubscriptions(newSubsList);
                    you.setNumSubscribed((you.getNumSubscribed()-1));
                }
            }
        }

        userRepository.save(you);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @RequestMapping(value="/getSubscriptions/", method = RequestMethod.GET)
    public List<String> subscriptions(@ModelAttribute("claims") Claims claims){
        return userRepository.findByUserName(claims.get("user").toString()).getSubscriptions();
    }

    @RequestMapping(value="/findSelf", method = RequestMethod.GET)
    public User findSelf(@ModelAttribute("claims") Claims claims){
        return userRepository.findByUserName(claims.get("user").toString());
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
