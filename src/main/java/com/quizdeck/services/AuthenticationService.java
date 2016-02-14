package com.quizdeck.services;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * A service designed to assist with the creation of auth tokens.
 *
 * Created by Brandon on 2/12/2016.
 */
@Service
public class AuthenticationService {
    @Resource(name = "secretKey")
    private String secretKey;

    /**
     * Creates a new auth token.
     *
     * @param username The username to be included in the token's claims.
     * @param role The role to be included in the token's claims.
     * @return A new auth token.
     */
    public String buildToken(String username, String role) {
        return Jwts
                .builder()
                .setSubject("QuizDeck")
                .claim("user", username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
