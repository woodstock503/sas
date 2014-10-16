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
import java.math.BigDecimal;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.AccountYear;

@Entity
public class AccountBalance implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8337386888637133526L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id = null;
	@Version
	@Column(name = "version")
	private int version = 0;

	@ManyToOne
	private ChartOfAccount chartOfAccount;

	@Column
	private BigDecimal debitBalance;

	@Column
	private BigDecimal creditBalance;

	@ManyToOne
	private Company company;

	@ManyToOne
	private AccountYear balanceAccountYear;

	@ManyToOne
	private AccountYear openingAccountYear;

	@Column
	private boolean isBeginBalance;
	
	@Column
	private String description;
	
	@Column
	private String descriptionEn;

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
			return id.equals(((AccountBalance) that).id);
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

	public Company getCompany()
	{
		return this.company;
	}

	public void setCompany(final Company company)
	{
		this.company = company;
	}

	public AccountYear getBalanceAccountYear()
	{
		return this.balanceAccountYear;
	}

	public void setBalanceAccountYear(final AccountYear balanceAccountYear)
	{
		this.balanceAccountYear = balanceAccountYear;
	}

	public AccountYear getOpeningAccountYear()
	{
		return this.openingAccountYear;
	}

	public void setOpeningAccountYear(final AccountYear openingAccountYear)
	{
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