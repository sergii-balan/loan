package com.balan.sergii.loan.service;

import java.util.List;

import com.balan.sergii.loan.dao.Loan;
import javassist.NotFoundException;

public interface LoanService {

	Loan create(Loan loan);
	Loan get(Long loanId) throws NotFoundException;
	Loan update(Loan loan);
	List<Loan> getList(Long userId);
}
