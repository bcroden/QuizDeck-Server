package com.quizdeck.controllers;

import com.jayway.jsonpath.JsonPath;
import com.quizdeck.QuizDeckApplication;
import com.quizdeck.model.database.User;
import com.quizdeck.model.inputs.CreateAccountInput;
import com.quizdeck.model.inputs.LoginInput;
import com.quizdeck.repositories.UserRepository;
import com.quizdeck.services.PassEncryption;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * Created by Brandon on 2/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
@WebAppConfiguration
public class AuthenticationControllerTest {
    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    @Resource(name = "secretKey")
    private String secretKey;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PassEncryption encrypt;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {
        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .get();
        Assert.assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void createAccountSuccess() throws Exception {
        System.out.println("**************************Removing testUser***********************");
        userRepository.removeByUserName("testUser");
        String result = mockMvc.perform(post("/rest/nonsecure/createAccount")
            .content(this.json(new CreateAccountInput("testUser", "password", "testUser@email.com")))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andReturn()
                .getResponse()
                .getContentAsString();

        String token = JsonPath.read(result, "$.token");
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();


        System.out.println("**************************Removing testUser***********************");
        userRepository.removeByUserName("testUser");

        assertThat(claims.getSubject(), is(equalTo("QuizDeck")));
        assertThat(claims.get("user"), is(equalTo("testUser")));
        assertThat(claims.get("role"), is(equalTo("User")));
        assertNotNull(claims.getIssuedAt());

    }

    @Test
    public void createAccountFailure() throws Exception {
        mockMvc.perform(post("/rest/nonsecure/createAccount")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }



    @Test
    public void loginSuccess() throws Exception {
        System.out.println("**************************Removing testUser***********************");
        userRepository.removeByUserName("testUser");

        ArrayList<byte[]> hashResult = encrypt.encryptAndSeed("password");
        String storedPass = Base64.getEncoder().encodeToString(hashResult.get(0));
        String storedSalt = Base64.getEncoder().encodeToString(hashResult.get(1));
        userRepository.save(new User("testUser", storedPass, storedSalt, "testUser@email.com", new Date()));

        String result = mockMvc.perform(post("/rest/nonsecure/login")
                .content(this.json(new LoginInput("testUser", "password")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn()
                    .getResponse()
                    .getContentAsString();

        String token = JsonPath.read(result, "$.token");
        Claims claims = Jwts
                .parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();

        System.out.println("**************************Removing testUser***********************");
        userRepository.removeByUserName("testUser");

        assertThat(claims.getSubject(), is(equalTo("QuizDeck")));
        assertThat(claims.get("user"), is(equalTo("testUser")));
        assertThat(claims.get("role"), is(equalTo("User")));
        assertNotNull(claims.getIssuedAt());
    }

    @Test
    public void loginFailure() throws Exception {
        mockMvc.perform(post("/rest/nonsecure/login")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
