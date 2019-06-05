package com.balan.sergii.loan.dao.listener;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import javax.persistence.PrePersist;
import com.balan.sergii.loan.service.dao.Loan;

public class LoanListener {
	
	private final static int CRITICAL_HRS_END = 8;
	private final static Double MAX_INTEREST = 1.5;
	
	@PrePersist
    private void onCreate(Loan loan) {
        checkInterestRate(loan);
    }
	
	private void checkInterestRate(Loan loan) {
		if (isInMaxRiskTimeInterval(loan.getStartDate())) {
			loan.setInterestRate(MAX_INTEREST);
		}
	}
	
	private boolean isInMaxRiskTimeInterval(final Instant date) {
		return LocalDateTime.ofInstant(date, ZoneOffset.UTC).getHour() < CRITICAL_HRS_END;
	}

}
