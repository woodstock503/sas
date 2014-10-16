package sas.saccplus.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import sas.saccplus.model.AccountBalance;
import sas.saccplus.model.Company;

public class DTOAccountYear {
	private Long id;
	private Date startDate;
	private Date endDate;
	private BigDecimal debitBalance;
	private BigDecimal creditBalance;
	private boolean closeStatus;
	private Company company;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean accountYearStatus;
	private Set<AccountBalance> accountBalances = new HashSet<AccountBalance>();	
	private Date dateFrom;
	private Date dateTo;
	
	
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
	public boolean isCloseStatus() {
		return closeStatus;
	}
	public void setCloseStatus(boolean closeStatus) {
		this.closeStatus = closeStatus;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
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
	public boolean isAccountYearStatus() {
		return accountYearStatus;
	}
	public void setAccountYearStatus(boolean accountYearStatus) {
		this.accountYearStatus = accountYearStatus;
	}
	public Set<AccountBalance> getAccountBalances() {
		return accountBalances;
	}
	public void setAccountBalances(Set<AccountBalance> accountBalances) {
		this.accountBalances = accountBalances;
	}
	public Date getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	public Date getDateTo() {
		return dateTo;
	}
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}
}
