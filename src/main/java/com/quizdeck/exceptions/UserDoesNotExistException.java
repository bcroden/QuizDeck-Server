package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Cade on 4/22/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="A user in this relationship doesn't exist")
public class UserDoesNotExistException extends Exception{
}
