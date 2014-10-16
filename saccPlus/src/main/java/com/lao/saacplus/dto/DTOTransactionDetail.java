package com.lao.saacplus.dto;

import java.math.BigDecimal;
import java.util.Date;

import com.lao.saacplus.entity.AccountBook;
import com.lao.saacplus.entity.AccountType;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.Branch;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.Department;
import com.lao.saacplus.entity.Period;
import com.lao.saacplus.entity.Rate;
import com.lao.saacplus.entity.TransactionMaster;


public class DTOTransactionDetail {
	private Long id;
	private Rate rate;
	private TransactionMaster transactionMaster;
	private ChartOfAccount chartOfAccount;
	private AccountType accountType;
	private Department department;
	private Company company;
	private Branch branch;
	private AccountYear accountYear;
	private AccountBook accountBook;
	private BigDecimal amountDebit;
	private BigDecimal amountCredit;
	private BigDecimal amountDebitLo;
	private BigDecimal amountCreditLo;
	private BigDecimal transactionRate;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean transactionDetailStatus;
	private Period period;
	private int periodNo;
	private String description;
	private String descriptionEn;
	private int orderNo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Rate getRate() {
		return rate;
	}
	public void setRate(Rate rate) {
		this.rate = rate;
	}
	public TransactionMaster getTransactionMaster() {
		return transactionMaster;
	}
	public void setTransactionMaster(TransactionMaster transactionMaster) {
		this.transactionMaster = transactionMaster;
	}
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Branch getBranch() {
		return branch;
	}
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public AccountYear getAccountYear() {
		return accountYear;
	}
	public void setAccountYear(AccountYear accountYear) {
		this.accountYear = accountYear;
	}
	public AccountBook getAccountBook() {
		return accountBook;
	}
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
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
	public BigDecimal getAmountDebitLo() {
		return amountDebitLo;
	}
	public void setAmountDebitLo(BigDecimal amountDebitLo) {
		this.amountDebitLo = amountDebitLo;
	}
	public BigDecimal getAmountCreditLo() {
		return amountCreditLo;
	}
	public void setAmountCreditLo(BigDecimal amountCreditLo) {
		this.amountCreditLo = amountCreditLo;
	}
	public BigDecimal getTransactionRate() {
		return transactionRate;
	}
	public void setTransactionRate(BigDecimal transactionRate) {
		this.transactionRate = transactionRate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDateTime() {
		return createDateTime;
	}
	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}
	public String getUpdateBy() {
		return updateBy;
	}
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	public Date getUpdateDateTime() {
		return updateDateTime;
	}
	public void setUpdateDateTime(Date updateDateTime) {
		this.updateDateTime = updateDateTime;
	}
	public boolean isTransactionDetailStatus() {
		return transactionDetailStatus;
	}
	public void setTransactionDetailStatus(boolean transactionDetailStatus) {
		this.transactionDetailStatus = transactionDetailStatus;
	}
	public Period getPeriod() {
		return period;
	}
	public void setPeriod(Period period) {
		this.period = period;
	}
	public int getPeriodNo() {
		return periodNo;
	}
	public void setPeriodNo(int periodNo) {
		this.periodNo = periodNo;
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
	public int getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}
}
