package com.quizdeck.services;

import com.quizdeck.QuizDeckApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Brandon on 2/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
public class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authService;

    @Resource(name = "secretKey")
    private String secretKey;

    @Test
    public void buildTokenSuccess() {
        String serviceTtoken = authService.buildToken("testUser", "User");
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(serviceTtoken)
                .getBody();

        assertThat(claims.get("user"), is(equalTo("testUser")));
        assertThat(claims.get("role"), is(equalTo("User")));

        serviceTtoken = authService.buildToken("otherUser", "Admin");
        claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(serviceTtoken)
                .getBody();

        assertThat(claims.get("user"), is(equalTo("otherUser")));
        assertThat(claims.get("role"), is(equalTo("Admin")));
    }
}
