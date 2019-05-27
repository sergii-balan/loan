package com.balan.sergii.loan.dao.listener;

import java.util.Date;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.joda.time.DateTime;
import com.balan.sergii.loan.dao.Loan;

public class LoanListener {
	
	private final static int CRITICAL_HRS_END = 8;
	private final static Double MAX_INTEREST = 1.5;
	
	@PrePersist
    private void onCreate(Loan loan) {
        checkInterestRate(loan);
    }
	
	@PreUpdate
    private void onUpdate(Loan loan) {
		extendLoan(loan);
    }
	
	private void checkInterestRate(Loan loan) {
		
		if (isInMaxRiskTimeInterval(loan.getStartDate())) {
			loan.setInterestRate(MAX_INTEREST);
		}
	}
	
	private boolean extendLoan(Loan loan) {
		return true;
	}
	
	private boolean isInMaxRiskTimeInterval(final Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getHourOfDay() < CRITICAL_HRS_END;
	}

}
