package sas.saccplus.dto;

import java.util.Date;


public class DTOCurrency {
	private Long id;
	private String country;
	private String countryEn;
	private String abbreviation;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean currencyStatus;



	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCountryEn() {
		return countryEn;
	}
	public void setCountryEn(String countryEn) {
		this.countryEn = countryEn;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
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
	public boolean isCurrencyStatus() {
		return currencyStatus;
	}
	public void setCurrencyStatus(boolean currencyStatus) {
		this.currencyStatus = currencyStatus;
	}

	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (country != null && !country.trim().isEmpty())
			result += ", country: " + country;
		if (countryEn != null && !countryEn.trim().isEmpty())
			result += ", countryEn: " + countryEn;
		if (abbreviation != null && !abbreviation.trim().isEmpty())
			result += ", abbreviation: " + abbreviation;
		if (createBy != null && !createBy.trim().isEmpty())
			result += ", createBy: " + createBy;
		if (updateBy != null && !updateBy.trim().isEmpty())
			result += ", updateBy: " + updateBy;
		result += ", currencyStatus: " + currencyStatus;
		return result;
	}
}
