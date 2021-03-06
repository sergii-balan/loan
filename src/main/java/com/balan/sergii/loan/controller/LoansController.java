package com.balan.sergii.loan.controller;

import java.util.List;

import javax.naming.LimitExceededException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.balan.sergii.loan.service.LoanService;
import com.balan.sergii.loan.service.dao.Loan;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/loans",
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseStatus(HttpStatus.OK)
public class LoansController {
    
	public static final String HDR_USER = "UserId";

	@Autowired
	private HttpServletRequest clientRequest;
	
	@Autowired
	private LoanService loanService;
	
    @PutMapping
    public Loan create(@RequestBody Loan loan) throws LimitExceededException {
    	loan.setIp(clientRequest.getRemoteAddr());
    	return loanService.create(loan);
    }
    
    @PostMapping
    public Loan update(@RequestBody Loan loan) throws NotFoundException {
    	loan.setIp(clientRequest.getRemoteAddr());
    	return loanService.extend(loan);
    }     
    
    @GetMapping
    public List<Loan> getList(@RequestHeader(HDR_USER) Long userId) {
        return loanService.getList(userId);
    }
    
    @GetMapping("/{loanId}")
    public List<Loan> get(@PathVariable Long loanId) throws NotFoundException {
    	return loanService.get(loanId);
    }
    
}