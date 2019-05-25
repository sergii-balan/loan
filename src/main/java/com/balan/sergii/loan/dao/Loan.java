package com.balan.sergii.loan.dao;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "loans")
public class Loan {
	@Id
	@GeneratedValue//(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;
	private BigDecimal amount;
	
	@CreationTimestamp
	private Date startDate;
	private Long termHrs;

	public Loan() {}

	public Loan(Long userId, BigDecimal amount, Long termHrs) {
		this.userId = userId;
		this.amount = amount;
		this.termHrs = termHrs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Long getTermHrs() {
		return termHrs;
	}

	public void setTermHrs(Long termHrs) {
		this.termHrs = termHrs;
	}

}
