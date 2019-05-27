package com.balan.sergii.loan.controller.advice;

import javax.naming.LimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class LoanExceptionsAdvice {

	@ExceptionHandler(LimitExceededException.class)
	@ResponseStatus(HttpStatus.LOCKED)
	@ResponseBody String limitExceededHandler(LimitExceededException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody String LoanNotFoundHandler(EntityNotFoundException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(EntityExistsException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody String LoanExistsHandler(EntityExistsException ex) {
		return ex.getMessage();
	}
}
