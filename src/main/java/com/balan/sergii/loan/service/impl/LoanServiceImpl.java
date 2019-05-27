package com.balan.sergii.loan.service.impl;

import java.util.List;
import java.util.Optional;

import javax.naming.LimitExceededException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.balan.sergii.loan.dao.Loan;
import com.balan.sergii.loan.service.LoanRepository;
import com.balan.sergii.loan.service.LoanService;

import javassist.NotFoundException;


@Service
public class LoanServiceImpl implements LoanService {

	private static final Long MAX_ALLOWED_TRIES_PER_IP = 3L;
	
	@Autowired
	private LoanRepository loanRepository;

	@Override
	public Loan create(Loan loan) throws LimitExceededException {
		if (MAX_ALLOWED_TRIES_PER_IP <= loanRepository.getIpHitsCountByUser(loan.getUserId(), loan.getIp(), loan.getStartDate())) {
			throw new LimitExceededException("Allowed limit exceeded for this IP");
		}
		return loanRepository.save(loan);
	}

	@Override
	public Loan get(Long loanId) throws NotFoundException {
		Optional<Loan> loan = loanRepository.findById(loanId);
		
		if (!loan.isPresent()) {
			throw new NotFoundException("Loan doesn't exists: " + loanId);
		}
		
		return loan.get();
	}

	@Override
	public Loan update(Loan loan) {
		return loanRepository.save(loan);
	}

	@Override
	public List<Loan> getList(Long userId) {
		return loanRepository.findByUserId(userId);
	}

}
