package com.lao.saacplus.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.lao.saacplus.entity.Currency;
import com.lao.saacplus.entity.Period;


public class DTOCompany {
	private Long id;
	private String companyName;
	private String companyNameEn;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean companyStatus;
	private Currency currency;
	private String digitPattern;
	private Date startDate;
	private Date endDate;
	private Set<Period> periods = new HashSet<Period>();
	private String address;
	private String telephone;
	private String fax;
	private String email;
	private String vision;
	private String mission;
	private byte[] logo;
	private String taxId;
	private Date establishDateTime;
	private int period;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyNameEn() {
		return companyNameEn;
	}
	public void setCompanyNameEn(String companyNameEn) {
		this.companyNameEn = companyNameEn;
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
	public boolean isCompanyStatus() {
		return companyStatus;
	}
	public void setCompanyStatus(boolean companyStatus) {
		this.companyStatus = companyStatus;
	}
	public Currency getCurrency() {
		return currency;
	}
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	public String getDigitPattern() {
		return digitPattern;
	}
	public void setDigitPattern(String digitPattern) {
		this.digitPattern = digitPattern;
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
	public Set<Period> getPeriods() {
		return periods;
	}
	public void setPeriods(Set<Period> periods) {
		this.periods = periods;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getVision() {
		return vision;
	}
	public void setVision(String vision) {
		this.vision = vision;
	}
	public String getMission() {
		return mission;
	}
	public void setMission(String mission) {
		this.mission = mission;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getTaxId() {
		return taxId;
	}
	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}
	public Date getEstablishDateTime() {
		return establishDateTime;
	}
	public void setEstablishDateTime(Date establishDateTime) {
		this.establishDateTime = establishDateTime;
	}
	public int getPeriod() {
		return period;
	}
	public void setPeriod(int period) {
		this.period = period;
	}

}
