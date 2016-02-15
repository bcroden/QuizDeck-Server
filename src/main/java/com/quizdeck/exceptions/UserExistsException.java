package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Returned if a user attempts to create an account that is already active
 * Created by Cade on 2/14/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="This username is already taken")
public class UserExistsException extends Exception {
}