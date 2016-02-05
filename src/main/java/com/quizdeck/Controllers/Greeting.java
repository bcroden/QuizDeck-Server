package com.quizdeck.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Cade on 2/4/2016.
 */
@RestController
public class Greeting {
    @RequestMapping("/")
    public String index(){
        return "Now WITNESS the PAHWAH of a fully operational SPRING BOOT APP";
    }
}
