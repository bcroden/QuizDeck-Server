package com.quizdeck.controllers;

import com.quizdeck.filters.AuthFilter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Brandon on 2/10/2016.
 */
@RestController
public class SecureController {

    @Resource(name = "secretKey")
    private String secretKey;

    @RequestMapping("/rest/secure/test")
    public Claims test(HttpServletRequest request) {
        Claims claims = (Claims) request.getAttribute(AuthFilter.CLAIMS_ATTRIBUTE);

        return claims;
    }

    @RequestMapping("/rest/nonsecure/jwt")
    public String jwt(HttpServletRequest request) {
        return Jwts
                .builder()
                .setSubject("Test")
                .claim("role", "User")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
