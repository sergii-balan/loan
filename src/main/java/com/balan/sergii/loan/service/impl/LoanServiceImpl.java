package com.balan.sergii.loan.service.impl;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;

import javax.naming.LimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balan.sergii.loan.dao.Loan;
import com.balan.sergii.loan.service.LoanRepository;
import com.balan.sergii.loan.service.LoanService;


@Service
public class LoanServiceImpl implements LoanService {

	private static final Long MAX_ALLOWED_TRIES_PER_IP = 3L;
	private static final Double MAX_INTEREST = 1.5;
	
	@Autowired
	private LoanRepository loanRepository;

	@Override
	public Loan create(Loan loan) throws LimitExceededException {
		if (loan.getId() != null && loanRepository.existsById(loan.getId())) {
			throw new EntityExistsException("Allowed limit exceeded for this IP");
		}
		
		if (MAX_ALLOWED_TRIES_PER_IP <= loanRepository.getIpHitsCount(loan.getIp(), loan.getStartDate())) {
			throw new LimitExceededException("Allowed limit exceeded for this IP");
		}
		return loanRepository.save(loan);
	}

	@Override
	public Loan get(Long loanId) {
		Optional<Loan> loan = loanRepository.findById(loanId);
		
		if (!loan.isPresent()) {
			throw new EntityNotFoundException("Loan doesn't exists: " + loanId);
		}
		
		return loan.get();
	}

	@Override
	public Loan update(Loan newLoan) {
		Optional<Loan> result = loanRepository.findById(newLoan.getId());
		if (!result.isPresent()) {
			throw new EntityNotFoundException("Loan doesn't exists: " + newLoan.getId());
		}
		
		Loan oldLoan = result.get();
		
		if(oldLoan.getCloseDate().before(newLoan.getCloseDate())) {
			newLoan.setId(null);
			newLoan.setMasterId(oldLoan.getId());
			newLoan.setStartDate(new DateTime(oldLoan.getCloseDate()).plusDays(1).toDate());
			newLoan.setInterestRate(MAX_INTEREST);
			
			return loanRepository.save(newLoan);
		}
		
		throw new InputMismatchException("Malformed loan");
	}

	@Override
	public List<Loan> getList(Long userId) {
		return loanRepository.findByUserId(userId);
	}

}
