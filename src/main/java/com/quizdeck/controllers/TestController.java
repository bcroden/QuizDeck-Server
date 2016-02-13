package com.quizdeck.controllers;

import io.jsonwebtoken.Claims;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * This is a temporary controller, designed to give access to some values while testing.
 * TODO: Delete before merging to prod
 * Created by Brandon on 2/12/2016.
 */
@RestController
@RequestMapping("/rest/secure/")
public class TestController {
    @Resource(name = "secretKey")
    private String secretKey;

    @RequestMapping("/getClaims")
    public Claims getClaims(@ModelAttribute("claims") Claims claims) {
        return claims;
    }

    @RequestMapping("/getKey")
    public String getSecretKey() {
        return secretKey;
    }
}
