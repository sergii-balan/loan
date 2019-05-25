package com.balan.sergii.loan.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.balan.sergii.loan.dao.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{

}
