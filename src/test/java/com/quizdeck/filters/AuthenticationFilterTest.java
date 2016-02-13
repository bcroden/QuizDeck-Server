package com.quizdeck.filters;

import com.quizdeck.QuizDeckApplication;
import com.quizdeck.services.AuthenticationService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by Brandon on 2/13/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = QuizDeckApplication.class)
public class AuthenticationFilterTest {
    @Resource(name = "secretKey")
    private String secretKey;
    @Autowired
    private AuthenticationService authService;

    private AuthenticationFilter filter;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;

    @Before
    public void setup() {
        this.filter = new AuthenticationFilter(this.secretKey);
        this.filter.init(new MockFilterConfig());

        this.request = new MockHttpServletRequest();
        this.response = new MockHttpServletResponse();
        this.chain = new MockFilterChain();
    }

    @After
    public void teardown() {
        this.filter.destroy();
    }

    @Test
    public void addsClaims() throws IOException, ServletException {
        String token = authService.buildToken("testUser", "User");

        request.setServletPath("/rest/secure/getClaims/");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        filter.doFilter(request, response, chain);

        assertNotNull(request.getAttribute(AuthenticationFilter.CLAIMS_ATTRIBUTE));
    }

    @Test
    public void errorsWithBadHeader() throws IOException, ServletException {
        request.setServletPath("/rest/secure/getClaims/");
        request.addHeader(HttpHeaders.AUTHORIZATION, "NotBearer");

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    public void errorsWithMalformedHeader() throws IOException, ServletException {
        request.setServletPath("/rest/secure/getClaims/");
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer abc.abc.abc");

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    public void errorsWithNoHeader() throws IOException, ServletException {
        request.setServletPath("/rest/secure/getClaims/");

        filter.doFilter(request, response, chain);

        assertThat(response.getStatus(), is(equalTo(HttpStatus.UNAUTHORIZED.value())));
    }
}
