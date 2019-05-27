package com.balan.sergii.loan.service;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.balan.sergii.loan.dao.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{
	@Query("SELECT l FROM Loan l  WHERE l.userId=:userId")
    List<Loan> findByUserId(@Param("userId") Long userId);
	
	@Query("SELECT count(*) FROM Loan l WHERE l.ip=:ip AND startDate>=:startDate")
	Long getIpHitsCount(@Param("ip") String ip, @Param("startDate") Date startDate);
	
	@Query("SELECT count(*) FROM Loan l WHERE l.userId=:userId AND l.ip=:ip AND startDate>=:startDate")
	Long getIpHitsCountByUser(@Param("userId") Long userId, @Param("ip") String ip, @Param("startDate") Date startDate);	
}