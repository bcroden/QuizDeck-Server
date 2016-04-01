package com.quizdeck.controllers;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cade on 3/10/2016.
 */

@RestController
@RequestMapping("/rest/nonsecure/server")
public class WakeUpController {

    @RequestMapping(value="/wakeup/{name}", method=RequestMethod.GET)
    public String wakeup(@PathVariable String name){
        return "Greetings, " + name;
    }

}
