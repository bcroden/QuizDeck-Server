package com.quizdeck.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Brandon on 2/10/2016.
 */
public class AuthFilter implements Filter {
    public static final String CLAIMS_ATTRIBUTE = "claims";

    public AuthFilter(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("You are not authenticated, please log in and try again.");

            log.warn("Unauthorized request to: " + request.getRequestURL() + " Cause: no auth token");
            return;
        }

        try {
            String token = header.substring(7); // The part after "Bearer "

            Claims claims = Jwts
                    .parser()
                    .setSigningKey(this.secretKey)
                    .parseClaimsJws(token)
                    .getBody();

            request.setAttribute(CLAIMS_ATTRIBUTE, claims);
        }
        catch(MalformedJwtException | SignatureException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write("Your login token is invalid, please log in and try again.");

            log.warn("Unauthorized request to: " + request.getRequestURL() + " Cause: invalid token");
            return;
        }

        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void destroy() {
    }

    private final String secretKey;
    private static final Logger log = LoggerFactory.getLogger(AuthFilter.class);
}
