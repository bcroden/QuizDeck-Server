package com.quizdeck.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by Cade on 5/1/2016.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Quiz does not exist")
public class QuizDoesNotExist extends Exception{
}
