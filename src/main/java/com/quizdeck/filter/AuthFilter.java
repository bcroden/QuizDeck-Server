package com.quizdeck.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
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

        String header = request.getHeader("Authorization");
        if(header == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid Authorization header.");
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
        catch(SignatureException e) {
            throw new ServletException("Invalid token.");
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
}
