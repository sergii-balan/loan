package com.balan.sergii.loan.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.lang.NonNull;

import com.balan.sergii.loan.dao.listener.LoanListener;

@Entity
//@Table(name = "loans")
@EntityListeners(LoanListener.class)
public class Loan {
	@Id
	@GeneratedValue//(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long masterId;
	@NonNull
	private Long userId;
	@NonNull
	private BigDecimal amount;
	@NonNull
	private Double interestRate;
	
	@CreationTimestamp
	private Date startDate;
	@NonNull
	private Date closeDate;
	private String ip;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}	
	
	public Long getUserId() {
		System.out.println(">>> - getUserId.");
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getIp() {
		System.out.println(">>> - getIp.");
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
