package com.quizdeck.services;

import com.quizdeck.QuizDeckApplication;
import org.bouncycastle.crypto.generators.BCrypt;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import java.security.SecureRandom;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

/**
 * Tests for PasswordHashingService
 *
 * Created by Brandon on 2/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class PasswordHashingServiceTest {
    @Resource
    private PasswordHashingService hashingService;

    private static String testPassword = "password";
    private static String saltedHashedPassword;

    @BeforeClass
    public static void generateHashedPassword() {
        byte[] passBytes = testPassword.getBytes();
        byte[] saltBytes = new byte[16];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(saltBytes);

        byte[] hashBytes = BCrypt.generate(passBytes, saltBytes, PasswordHashingService.HASH_STRENGTH);

        String salt = Base64Utils.encodeToString(saltBytes);
        String hash = Base64Utils.encodeToString(hashBytes);

        saltedHashedPassword = salt + hash;
    }

    @Test
    public void checksThatPasswordsAreValid() {
        assertThat(hashingService.checkPassword(testPassword, saltedHashedPassword), is(true));
    }

    @Test
    public void checksThatPasswordsAreInvalid() {
        assertThat(hashingService.checkPassword("wrongPassword", saltedHashedPassword), is(false));
    }
}
