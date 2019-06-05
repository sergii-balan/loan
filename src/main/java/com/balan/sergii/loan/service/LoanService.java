package com.balan.sergii.loan.service;

import java.util.List;

import javax.naming.LimitExceededException;

import com.balan.sergii.loan.dao.Loan;

public interface LoanService {

	Loan create(Loan loan) throws LimitExceededException;
	List<Loan> get(Long loanId);
	Loan extend(Loan loanExtension);
	List<Loan> getList(Long userId);
}
