package com.quizdeck.services;

import com.quizdeck.QuizDeckApplication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
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
    public void buildUserTokenSuccess() {
        String token = authService.buildToken("testUser", "User");
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject(), is(equalTo("QuizDeck")));
        assertThat(claims.get("user"), is(equalTo("testUser")));
        assertThat(claims.get("role"), is(equalTo("User")));
        assertNotNull(claims.getIssuedAt());
    }

    @Test
    public void buildAdminTokenSuccess() {
        String token = authService.buildToken("otherUser", "Admin");
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();

        assertThat(claims.getSubject(), is(equalTo("QuizDeck")));
        assertThat(claims.get("user"), is(equalTo("otherUser")));
        assertThat(claims.get("role"), is(equalTo("Admin")));
        assertNotNull(claims.getIssuedAt());
    }
}
