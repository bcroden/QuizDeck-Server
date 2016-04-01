package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Cade on 3/12/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Tried to submit answer to inactive quiz")
public class InactiveQuizException extends Exception{
}
