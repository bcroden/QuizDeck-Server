package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Cade on 3/12/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="This quiz Id is not active")
public class InactiveQuizException extends Exception{
}
