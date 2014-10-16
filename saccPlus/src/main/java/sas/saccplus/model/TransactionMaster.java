package sas.saccplus.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;

import sas.saccplus.model.Period;
import sas.saccplus.model.TransactionDetail;

import java.math.BigDecimal;
import java.lang.Override;

@Entity
public class TransactionMaster implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 275393794196191853L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	private Company company;

	@ManyToOne
	private Branch branch;

	@ManyToOne
	private AccountYear accountYear;

	@ManyToOne
	private AccountBook accountBook;

	@ManyToOne
	private Users createUser;

	@Temporal(TemporalType.TIMESTAMP)
	private Date billDate;

	@Column
	private String certifyNo;

	@Column
	private String referenceNo;

	@Column
	private String description;
	
	@Column
	private String descriptionEn;

	@Column
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	@Column
	private String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDateTime;

	@Column
	private boolean transactionMasterStatus;

	@OneToMany(mappedBy = "transactionMaster", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<TransactionDetail> transactionDetails = new HashSet<TransactionDetail>();

	@Column
	private BigDecimal totalAmountDebit;

	@Column
	private BigDecimal totalAmountDebitLo;

	@Column
	private BigDecimal totalAmountCredit;

	@Column
	private BigDecimal totalAmountCreditLo;

	@ManyToOne
	private Period period;

	@Column
	private int periodNo;

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
			return id.equals(((TransactionMaster) that).id);
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

	public Date getBillDate()
	{
		return this.billDate;
	}

	public void setBillDate(final Date billDate)
	{
		this.billDate = billDate;
	}

	public String getCertifyNo()
	{
		return this.certifyNo;
	}

	public void setCertifyNo(final String certifyNo)
	{
		this.certifyNo = certifyNo;
	}

	public String getReferenceNo()
	{
		return this.referenceNo;
	}

	public void setReferenceNo(final String referenceNo)
	{
		this.referenceNo = referenceNo;
	}

	public Users getCreateUser()
	{
		return this.createUser;
	}

	public void setCreateUser(final Users createUser)
	{
		this.createUser = createUser;
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

	public String getDescription()
	{
		return this.description;
	}

	public void setDescription(final String description)
	{
		this.description = description;
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

	public boolean getTransactionMasterStatus()
	{
		return this.transactionMasterStatus;
	}

	public void setTransactionMasterStatus(final boolean transactionMasterStatus)
	{
		this.transactionMasterStatus = transactionMasterStatus;
	}

	public Set<TransactionDetail> getTransactionDetails()
	{
		return this.transactionDetails;
	}

	public void setTransactionDetails(
			final Set<TransactionDetail> transactionDetails)
	{
		this.transactionDetails = transactionDetails;
	}

	public BigDecimal getTotalAmountDebit()
	{
		return this.totalAmountDebit;
	}

	public void setTotalAmountDebit(final BigDecimal totalAmountDebit)
	{
		this.totalAmountDebit = totalAmountDebit;
	}

	public BigDecimal getTotalAmountDebitLo()
	{
		return this.totalAmountDebitLo;
	}

	public void setTotalAmountDebitLo(final BigDecimal totalAmountDebitLo)
	{
		this.totalAmountDebitLo = totalAmountDebitLo;
	}

	public BigDecimal getTotalAmountCredit()
	{
		return this.totalAmountCredit;
	}

	public void setTotalAmountCredit(final BigDecimal totalAmountCredit)
	{
		this.totalAmountCredit = totalAmountCredit;
	}

	public BigDecimal getTotalAmountCreditLo()
	{
		return this.totalAmountCreditLo;
	}

	public void setTotalAmountCreditLo(final BigDecimal totalAmountCreditLo)
	{
		this.totalAmountCreditLo = totalAmountCreditLo;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		result += "serialVersionUID: " + serialVersionUID;
		if (certifyNo != null && !certifyNo.trim().isEmpty())
			result += ", certifyNo: " + certifyNo;
		if (referenceNo != null && !referenceNo.trim().isEmpty())
			result += ", referenceNo: " + referenceNo;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		if (createBy != null && !createBy.trim().isEmpty())
			result += ", createBy: " + createBy;
		if (updateBy != null && !updateBy.trim().isEmpty())
			result += ", updateBy: " + updateBy;
		result += ", transactionMasterStatus: " + transactionMasterStatus;
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

	public String getDescriptionEn() {
		return descriptionEn;
	}

	public void setDescriptionEn(String descriptionEn) {
		this.descriptionEn = descriptionEn;
	}
}