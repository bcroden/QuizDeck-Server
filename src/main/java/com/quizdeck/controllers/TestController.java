package com.quizdeck.controllers;

import com.quizdeck.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * This is a temporary controller, designed to give access to some values while testing.
 * TODO: Delete before merging to prod
 * Created by Brandon on 2/12/2016.
 */
@RestController
@RequestMapping("/rest/secure/")
public class TestController {
    @Autowired
    private AuthenticationService authService;

    @Resource(name = "secretKey")
    private String secretKey;

    @RequestMapping("/getClaims")
    public Claims getClaims(HttpServletRequest request) {
        Claims claims = authService.getClaims(request);

        return claims;
    }

    @RequestMapping("/getKey")
    public String getSecretKey() {
        return secretKey;
    }
}
