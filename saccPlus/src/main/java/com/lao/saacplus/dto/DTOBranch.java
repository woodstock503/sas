package com.lao.saacplus.dto;

import java.util.Date;

import com.lao.saacplus.entity.Company;


public class DTOBranch {

	private Long id;
	private String branchName;
	private String branchNameEn;
	private boolean branchStatus;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private String address;
	private String telephone;
	private String fax;
	private String email;
	private String vision;
	private String mission;
	private byte[] logo;
	private String taxId;
	private Company company;
	private Date establishDateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchNameEn() {
		return branchNameEn;
	}
	public void setBranchNameEn(String branchNameEn) {
		this.branchNameEn = branchNameEn;
	}
	public boolean isBranchStatus() {
		return branchStatus;
	}
	public void setBranchStatus(boolean branchStatus) {
		this.branchStatus = branchStatus;
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Date getEstablishDateTime() {
		return establishDateTime;
	}
	public void setEstablishDateTime(Date establishDateTime) {
		this.establishDateTime = establishDateTime;
	}

}
