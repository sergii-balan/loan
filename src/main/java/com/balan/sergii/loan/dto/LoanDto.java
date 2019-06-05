package com.balan.sergii.loan.dto;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.lang.NonNull;

public class LoanDto {
	private Long id;
	private Long masterId;

	private Long userId;
	@NonNull
	private BigDecimal amount;
	@NonNull
	private Double interestRate;
	
	@NonNull
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
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
}
