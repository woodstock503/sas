package sas.saccplus.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import sas.saccplus.model.AccountBook;
import sas.saccplus.model.AccountYear;
import sas.saccplus.model.Branch;
import sas.saccplus.model.Company;
import sas.saccplus.model.Period;
import sas.saccplus.model.TransactionDetail;
import sas.saccplus.model.Users;

public class DTOTransactionMaster {
	private Long id;
	private Company company;
	private Branch branch;
	private AccountYear accountYear;
	private AccountBook accountBook;
	private Users createUser;
	private Date billDate;
	private String certifyNo;
	private String referenceNo;
	private String description;
	private String descriptionEn;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean transactionMasterStatus;
	private Set<TransactionDetail> transactionDetails = new HashSet<TransactionDetail>();
	private BigDecimal totalAmountDebit;
	private BigDecimal totalAmountDebitLo;
	private BigDecimal totalAmountCredit;
	private BigDecimal totalAmountCreditLo;
	private Period period;
	private int periodNo;
	
	
	private Date dateFrom;
	private Date dateTo;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public Users getCreateUser() {
		return createUser;
	}
	public void setCreateUser(Users createUser) {
		this.createUser = createUser;
	}
	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	public String getCertifyNo() {
		return certifyNo;
	}
	public void setCertifyNo(String certifyNo) {
		this.certifyNo = certifyNo;
	}
	public String getReferenceNo() {
		return referenceNo;
	}
	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public boolean isTransactionMasterStatus() {
		return transactionMasterStatus;
	}
	public void setTransactionMasterStatus(boolean transactionMasterStatus) {
		this.transactionMasterStatus = transactionMasterStatus;
	}
	public Set<TransactionDetail> getTransactionDetails() {
		return transactionDetails;
	}
	public void setTransactionDetails(Set<TransactionDetail> transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public BigDecimal getTotalAmountDebit() {
		return totalAmountDebit;
	}
	public void setTotalAmountDebit(BigDecimal totalAmountDebit) {
		this.totalAmountDebit = totalAmountDebit;
	}
	public BigDecimal getTotalAmountDebitLo() {
		return totalAmountDebitLo;
	}
	public void setTotalAmountDebitLo(BigDecimal totalAmountDebitLo) {
		this.totalAmountDebitLo = totalAmountDebitLo;
	}
	public BigDecimal getTotalAmountCredit() {
		return totalAmountCredit;
	}
	public void setTotalAmountCredit(BigDecimal totalAmountCredit) {
		this.totalAmountCredit = totalAmountCredit;
	}
	public BigDecimal getTotalAmountCreditLo() {
		return totalAmountCreditLo;
	}
	public void setTotalAmountCreditLo(BigDecimal totalAmountCreditLo) {
		this.totalAmountCreditLo = totalAmountCreditLo;
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
	public String getDescriptionEn() {
		return descriptionEn;
	}
	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}	
	
}
