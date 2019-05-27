package com.balan.sergii.loan.controller.advice;

import javax.naming.LimitExceededException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

//@ControllerAdvice
public class LimitExceededAdvice {

	@ExceptionHandler(LimitExceededException.class)
	@ResponseStatus(HttpStatus.LOCKED)
	@ResponseBody String limitExceededHandler(LimitExceededException ex) {
		return ex.getMessage();
	}
}
