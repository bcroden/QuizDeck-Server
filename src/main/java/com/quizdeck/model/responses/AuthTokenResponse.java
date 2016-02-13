package com.quizdeck.model.responses;

/**
 * Created by Brandon on 2/12/2016.
 */
public class AuthTokenResponse {
    private String token;

    public AuthTokenResponse() {
    }

    public AuthTokenResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
