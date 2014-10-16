package com.lao.saacplus.dto;

import java.util.Date;

import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.Company;


public class DTOPeriod {
	private Long id;
	private Date startDate;
	private Date endDate;
	private Company company;
	private int periodNo;
	private boolean closeStatus;
	private AccountYear accountYear;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
	}
	public boolean isCloseStatus() {
		return closeStatus;
	}
	public void setCloseStatus(boolean closeStatus) {
		this.closeStatus = closeStatus;
	}
	public AccountYear getAccountYear() {
		return accountYear;
	}
	public void setAccountYear(AccountYear accountYear) {
		this.accountYear = accountYear;
	}
	
	
	
}
