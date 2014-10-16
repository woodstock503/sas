package com.lao.saacplus.dto;

import java.math.BigDecimal;

import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.Company;


public class DTOAccountBalance {
	private Long id;
	private ChartOfAccount chartOfAccount;
	private BigDecimal debitBalance;
	private BigDecimal creditBalance;
	private Company company;
	private AccountYear balanceAccountYear;
	private AccountYear openingAccountYear;
	private boolean isBeginBalance;
	private String description;
	private String descriptionEn;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public ChartOfAccount getChartOfAccount() {
		return chartOfAccount;
	}
	public void setChartOfAccount(ChartOfAccount chartOfAccount) {
		this.chartOfAccount = chartOfAccount;
	}
	public BigDecimal getDebitBalance() {
		return debitBalance;
	}
	public void setDebitBalance(BigDecimal debitBalance) {
		this.debitBalance = debitBalance;
	}
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public AccountYear getBalanceAccountYear() {
		return balanceAccountYear;
	}
	public void setBalanceAccountYear(AccountYear balanceAccountYear) {
		this.balanceAccountYear = balanceAccountYear;
	}
	public AccountYear getOpeningAccountYear() {
		return openingAccountYear;
	}
	public void setOpeningAccountYear(AccountYear openingAccountYear) {
		this.openingAccountYear = openingAccountYear;
	}
	public boolean isBeginBalance() {
		return isBeginBalance;
	}
	public void setBeginBalance(boolean isBeginBalance) {
		this.isBeginBalance = isBeginBalance;
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
}
