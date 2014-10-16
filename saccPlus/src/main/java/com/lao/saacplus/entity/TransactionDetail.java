package com.lao.saacplus.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import com.lao.saacplus.entity.ChartOfAccount;
import javax.persistence.ManyToOne;
import com.lao.saacplus.entity.AccountType;
import com.lao.saacplus.entity.Department;
import java.math.BigDecimal;
import com.lao.saacplus.entity.Rate;
import com.lao.saacplus.entity.TransactionMaster;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.Branch;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.AccountBook;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.lao.saacplus.entity.Period;

@Entity
public class TransactionDetail implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 505156697665547056L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	private Rate rate;

	@ManyToOne
	private TransactionMaster transactionMaster;

	@ManyToOne
	private ChartOfAccount chartOfAccount;

	@ManyToOne
	private AccountType accountType;

	@ManyToOne
	private Department department;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Branch branch;

	@ManyToOne
	private AccountYear accountYear;

	@ManyToOne
	private AccountBook accountBook;

	@Column
	private BigDecimal amountDebit;

	@Column
	private BigDecimal amountCredit;

	@Column
	private BigDecimal amountDebitLo;

	@Column
	private BigDecimal amountCreditLo;

	@Column
	private BigDecimal transactionRate;

	@Column
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	@Column
	private String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDateTime;

	@Column
	private boolean transactionDetailStatus;

	@ManyToOne
	private Period period;

	@Column
	private int periodNo;
	
	@Column
	private String description;
	
	@Column
	private String descriptionEn;
	
	@Column
	private int orderNo;


	public Long getId()
	{
		return this.id;
	}

	public void setId(final Long id)
	{
		this.id = id;
	}

	public int getVersion()
	{
		return this.version;
	}

	public void setVersion(final int version)
	{
		this.version = version;
	}

	@Override
	public boolean equals(Object that)
	{
		if (this == that)
		{
			return true;
		}
		if (that == null)
		{
			return false;
		}
		if (getClass() != that.getClass())
		{
			return false;
		}
		if (id != null)
		{
			return id.equals(((TransactionDetail) that).id);
		}
		return super.equals(that);
	}

	@Override
	public int hashCode()
	{
		if (id != null)
		{
			return id.hashCode();
		}
		return super.hashCode();
	}

	public ChartOfAccount getChartOfAccount()
	{
		return this.chartOfAccount;
	}

	public void setChartOfAccount(final ChartOfAccount chartOfAccount)
	{
		this.chartOfAccount = chartOfAccount;
	}

	public AccountType getAccountType()
	{
		return this.accountType;
	}

	public void setAccountType(final AccountType accountType)
	{
		this.accountType = accountType;
	}

	public Department getDepartment()
	{
		return this.department;
	}

	public void setDepartment(final Department department)
	{
		this.department = department;
	}

	public BigDecimal getAmountDebit()
	{
		return this.amountDebit;
	}

	public void setAmountDebit(final BigDecimal amountDebit)
	{
		this.amountDebit = amountDebit;
	}

	public BigDecimal getAmountCredit()
	{
		return this.amountCredit;
	}

	public void setAmountCredit(final BigDecimal amountCredit)
	{
		this.amountCredit = amountCredit;
	}

	public BigDecimal getAmountDebitLo()
	{
		return this.amountDebitLo;
	}

	public void setAmountDebitLo(final BigDecimal amountDebitLo)
	{
		this.amountDebitLo = amountDebitLo;
	}

	public BigDecimal getAmountCreditLo()
	{
		return this.amountCreditLo;
	}

	public void setAmountCreditLo(final BigDecimal amountCreditLo)
	{
		this.amountCreditLo = amountCreditLo;
	}

	public Rate getRate()
	{
		return this.rate;
	}

	public void setRate(final Rate rate)
	{
		this.rate = rate;
	}

	public BigDecimal getTransactionRate()
	{
		return this.transactionRate;
	}

	public void setTransactionRate(final BigDecimal transactionRate)
	{
		this.transactionRate = transactionRate;
	}

	public TransactionMaster getTransactionMaster()
	{
		return this.transactionMaster;
	}

	public void setTransactionMaster(final TransactionMaster transactionMaster)
	{
		this.transactionMaster = transactionMaster;
	}

	public Company getCompany()
	{
		return this.company;
	}

	public void setCompany(final Company company)
	{
		this.company = company;
	}

	public Branch getBranch()
	{
		return this.branch;
	}

	public void setBranch(final Branch branch)
	{
		this.branch = branch;
	}

	public AccountYear getAccountYear()
	{
		return this.accountYear;
	}

	public void setAccountYear(final AccountYear accountYear)
	{
		this.accountYear = accountYear;
	}

	public AccountBook getAccountBook()
	{
		return this.accountBook;
	}

	public void setAccountBook(final AccountBook accountBook)
	{
		this.accountBook = accountBook;
	}

	public String getCreateBy()
	{
		return this.createBy;
	}

	public void setCreateBy(final String createBy)
	{
		this.createBy = createBy;
	}

	public Date getCreateDateTime()
	{
		return this.createDateTime;
	}

	public void setCreateDateTime(final Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	public String getUpdateBy()
	{
		return this.updateBy;
	}

	public void setUpdateBy(final String updateBy)
	{
		this.updateBy = updateBy;
	}

	public Date getUpdateDateTime()
	{
		return this.updateDateTime;
	}

	public void setUpdateDateTime(final Date updateDateTime)
	{
		this.updateDateTime = updateDateTime;
	}

	public boolean getTransactionDetailStatus()
	{
		return this.transactionDetailStatus;
	}

	public void setTransactionDetailStatus(final boolean transactionDetailStatus)
	{
		this.transactionDetailStatus = transactionDetailStatus;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		result += "serialVersionUID: " + serialVersionUID;
		if (createBy != null && !createBy.trim().isEmpty())
			result += ", createBy: " + createBy;
		if (updateBy != null && !updateBy.trim().isEmpty())
			result += ", updateBy: " + updateBy;
		result += ", transactionDetailStatus: " + transactionDetailStatus;
		return result;
	}

	public Period getPeriod()
	{
		return this.period;
	}

	public void setPeriod(final Period period)
	{
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