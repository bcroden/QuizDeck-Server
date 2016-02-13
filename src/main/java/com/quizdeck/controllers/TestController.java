package com.quizdeck.controllers;

import com.quizdeck.exceptions.ForbiddenAccessException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public String getSecretKey(@ModelAttribute("claims") Claims claims) throws ForbiddenAccessException {
        if(!claims.get("role").equals("Admin")) {
            log.warn("Unauthorized attempt to access: /rest/secure/getKey");
            throw new ForbiddenAccessException();
        }
        return secretKey;
    }

    private Logger log = LoggerFactory.getLogger(TestController.class);
}
