package com.balan.sergii.loan.controller;

import java.util.List;

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

import com.balan.sergii.loan.dao.Loan;
import com.balan.sergii.loan.service.LoanService;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/loans",
				produces = MediaType.APPLICATION_JSON_VALUE,
				consumes = MediaType.APPLICATION_JSON_VALUE)
@ResponseStatus(HttpStatus.OK)
public class Loans {
    
	private static final String HDR_USER = "UserId";

	@Autowired
	private HttpServletRequest clientRequest;
	
	@Autowired
	private LoanService loanService;
	
    @PutMapping
    public void create(@RequestBody Loan loan) {
    	loanService.create(clientRequest.getRemoteAddr(), loan);
    } 
   
    @PostMapping
    public void update(@RequestBody Loan loan) {
    	loanService.update(loan);
    }     
    
    @GetMapping
    public List<Loan> getList(@RequestHeader(HDR_USER) Long userId) {
        return loanService.getList(userId);
    }
    
    @GetMapping("/{loanId}")
    public Loan get(@PathVariable Long loanId) throws NotFoundException {
    	return loanService.get(loanId);
    }
    
}