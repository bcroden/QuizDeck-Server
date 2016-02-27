package com.quizdeck.model.responses;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO containing an auth token.
 *
 * Created by Brandon on 2/12/2016.
 */
@Getter
@Setter
public class AuthTokenResponse {
    private String token;

    public AuthTokenResponse() {
    }

    public AuthTokenResponse(String token) {
        this.token = token;
    }
}
