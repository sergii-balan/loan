package com.balan.sergii.loan.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javassist.NotFoundException;

@ControllerAdvice
public class LoanNotFoundAdvice {

	@ExceptionHandler(NotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody String employeeNotFoundHandler(NotFoundException ex) {
		return ex.getMessage();
	}
}
