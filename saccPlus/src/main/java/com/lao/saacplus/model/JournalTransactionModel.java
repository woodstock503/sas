package com.lao.saacplus.model;

import java.math.BigDecimal;

import com.lao.saacplus.entity.AccountType;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.Department;
import com.lao.saacplus.entity.Rate;



public class JournalTransactionModel {
	private ChartOfAccount chartOfAccount;
	private AccountType accountType;
	private Department department;
	private Rate currenyRate;
	private String description;
	private String descriptionEn;
	private BigDecimal amountDebit;
	private BigDecimal amountCredit;
	private BigDecimal loAmountDebit;
	private BigDecimal loAmountCredit;
	private BigDecimal rate;
	private Long id;
	
	
	
	
	
	public ChartOfAccount getChartOfAccount() {
		return chartOfAccount;
	}
	public void setChartOfAccount(ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Rate getCurrenyRate() {
		return currenyRate;
	}
	public void setCurrenyRate(Rate currenyRate) {
		this.currenyRate = currenyRate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
	public BigDecimal getAmountDebit() {
		return amountDebit;
	}
	public void setAmountDebit(BigDecimal amountDebit) {
		this.amountDebit = amountDebit;
	}
	public BigDecimal getAmountCredit() {
		return amountCredit;
	}
	public void setAmountCredit(BigDecimal amountCredit) {
		this.amountCredit = amountCredit;
	}
	public BigDecimal getLoAmountDebit() {
		return loAmountDebit;
	}
	public void setLoAmountDebit(BigDecimal loAmountDebit) {
		this.loAmountDebit = loAmountDebit;
	}
	public BigDecimal getLoAmountCredit() {
		return loAmountCredit;
	}
	public void setLoAmountCredit(BigDecimal loAmountCredit) {
		this.loAmountCredit = loAmountCredit;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
