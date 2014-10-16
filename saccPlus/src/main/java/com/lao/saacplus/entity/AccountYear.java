package com.lao.saacplus.entity;

import javax.persistence.Entity;
import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import com.lao.saacplus.entity.Company;
import javax.persistence.ManyToOne;
import com.lao.saacplus.entity.AccountBalance;
import java.util.Set;
import java.util.HashSet;
import javax.persistence.OneToMany;

@Entity
public class AccountYear implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6499824194576842797L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;

	@Column
	private BigDecimal debitBalance;

	@Column
	private BigDecimal creditBalance;

	@Column
	private boolean closeStatus;

	@ManyToOne
	private Company company;

	@Column
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDateTime;

	@Column
	private String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDateTime;

	@Column
	private boolean accountYearStatus;

	@OneToMany(mappedBy="balanceAccountYear",cascade = CascadeType.ALL,orphanRemoval=true)
	private Set<AccountBalance> accountBalances = new HashSet<AccountBalance>();

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
			return id.equals(((AccountYear) that).id);
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

	public Date getStartDate()
	{
		return this.startDate;
	}

	public void setStartDate(final Date startDate)
	{
		this.startDate = startDate;
	}

	public Date getEndDate()
	{
		return this.endDate;
	}

	public void setEndDate(final Date endDate)
	{
		this.endDate = endDate;
	}

	public BigDecimal getDebitBalance()
	{
		return this.debitBalance;
	}

	public void setDebitBalance(final BigDecimal debitBalance)
	{
		this.debitBalance = debitBalance;
	}

	public BigDecimal getCreditBalance()
	{
		return this.creditBalance;
	}

	public void setCreditBalance(final BigDecimal creditBalance)
	{
		this.creditBalance = creditBalance;
	}

	public boolean getCloseStatus()
	{
		return this.closeStatus;
	}

	public void setCloseStatus(final boolean closeStatus)
	{
		this.closeStatus = closeStatus;
	}

	public Company getCompany()
	{
		return this.company;
	}

	public void setCompany(final Company company)
	{
		this.company = company;
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

	public boolean getAccountYearStatus()
	{
		return this.accountYearStatus;
	}

	public void setAccountYearStatus(final boolean accountYearStatus)
	{
		this.accountYearStatus = accountYearStatus;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		result += "closeStatus: " + closeStatus;
		if (createBy != null && !createBy.trim().isEmpty())
			result += ", createBy: " + createBy;
		if (updateBy != null && !updateBy.trim().isEmpty())
			result += ", updateBy: " + updateBy;
		result += ", accountYearStatus: " + accountYearStatus;
		return result;
	}

	public Set<AccountBalance> getAccountBalances()
	{
		return this.accountBalances;
	}

	public void setAccountBalances(final Set<AccountBalance> accountBalances)
	{
		this.accountBalances = accountBalances;
	}
}