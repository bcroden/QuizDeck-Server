package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Cade on 2/14/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Incorrect Username or Password")
public class UsernamePassException extends Exception {
}