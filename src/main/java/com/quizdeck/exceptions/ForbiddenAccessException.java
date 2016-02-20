package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Brandon on 2/13/2016.
 */
@ResponseStatus(value= HttpStatus.FORBIDDEN, reason="You do not have the required permissions to access this content")
public class ForbiddenAccessException extends Exception {
}
