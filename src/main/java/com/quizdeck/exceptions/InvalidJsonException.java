package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * An exception for when JSON inputs are invalid and cannot be used.
 *
 * Created by Brandon on 2/13/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid json")
public class InvalidJsonException extends Exception {
}
