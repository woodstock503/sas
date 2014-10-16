package sas.saccplus.dto;

import java.util.Date;

import sas.saccplus.model.AccountType;
import sas.saccplus.model.Company;
import sas.saccplus.model.Currency;

public class DTOChartOfAccount {

	private Long id;
	private String chartOfAccountCode;
	private String chartOfAccountCodeMain;
	private String chartOfAccountName;
	private String chartOfAccountNameEn;
	private Currency currency;
	private boolean chartOfAccountStatus;
	private AccountType accountType;
	private Company company;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getChartOfAccountCode() {
		return chartOfAccountCode;
	}
	public void setChartOfAccountCode(String chartOfAccountCode) {
		this.chartOfAccountCode = chartOfAccountCode;
	}
	public String getChartOfAccountCodeMain() {
		return chartOfAccountCodeMain;
	}
	public void setChartOfAccountCodeMain(String chartOfAccountCodeMain) {
		this.chartOfAccountCodeMain = chartOfAccountCodeMain;
	}
	public String getChartOfAccountName() {
		return chartOfAccountName;
	}
	public void setChartOfAccountName(String chartOfAccountName) {
		this.chartOfAccountName = chartOfAccountName;
	}
	public String getChartOfAccountNameEn() {
		return chartOfAccountNameEn;
	}
	public void setChartOfAccountNameEn(String chartOfAccountNameEn) {
		this.chartOfAccountNameEn = chartOfAccountNameEn;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public boolean isChartOfAccountStatus() {
		return chartOfAccountStatus;
	}
	public void setChartOfAccountStatus(boolean chartOfAccountStatus) {
		this.chartOfAccountStatus = chartOfAccountStatus;
	}
	public AccountType getAccountType() {
		return accountType;
	}
	public void setAccountType(AccountType accountType) {
		this.accountType = accountType;
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
}
