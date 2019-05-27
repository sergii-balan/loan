package com.balan.sergii.loan.service;

import java.util.List;

import javax.naming.LimitExceededException;

import com.balan.sergii.loan.dao.Loan;
import javassist.NotFoundException;

public interface LoanService {

	Loan create(Loan loan) throws LimitExceededException;
	Loan get(Long loanId) throws NotFoundException;
	Loan update(Loan loan);
	List<Loan> getList(Long userId);
}
