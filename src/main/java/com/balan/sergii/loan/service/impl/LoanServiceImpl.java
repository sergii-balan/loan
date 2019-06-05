package com.balan.sergii.loan.service.impl;

import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.List;
import javax.naming.LimitExceededException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balan.sergii.loan.service.LoanRepository;
import com.balan.sergii.loan.service.LoanService;
import com.balan.sergii.loan.service.dao.Loan;


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
	public List<Loan> get(Long loanId) {
		List<Loan> loans = loanRepository.getLoansById(loanId);
		
		if (loans == null || loans.isEmpty() ) {
			throw new EntityNotFoundException("Loan doesn't exists: " + loanId);
		}
		
		return loans;
	}

	@Override
	public Loan extend(Loan loanExtension) {
		Loan loan = loanRepository
					.findById(loanExtension.getId())
					.orElseThrow(()-> new EntityNotFoundException("Loan doesn't exists: " + loanExtension.getId()));
		
		if(loan.getCloseDate().isBefore(loanExtension.getCloseDate())) {
			loanExtension.setId(null);
			loanExtension.setMasterId(loan.getId());
			loanExtension.setStartDate(loan.getCloseDate().plus(1, ChronoUnit.DAYS));
			loanExtension.setInterestRate(MAX_INTEREST);
			
			return loanRepository.save(loanExtension);
		}
		
		throw new InputMismatchException("Malformed loan");
	}

	@Override
	public List<Loan> getList(Long userId) {
		return loanRepository.findByUserId(userId);
	}

}
