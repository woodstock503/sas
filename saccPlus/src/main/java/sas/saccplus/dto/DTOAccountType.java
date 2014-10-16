package sas.saccplus.dto;

import java.util.Date;

public class DTOAccountType {
	private Long id;
	private String accountTypeName;
	private String accountTypeNameEn;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean accountTypeStatus;
	private String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountTypeName() {
		return accountTypeName;
	}
	public void setAccountTypeName(String accountTypeName) {
		this.accountTypeName = accountTypeName;
	}
	public String getAccountTypeNameEn() {
		return accountTypeNameEn;
	}
	public void setAccountTypeNameEn(String accountTypeNameEn) {
		this.accountTypeNameEn = accountTypeNameEn;
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
	public boolean isAccountTypeStatus() {
		return accountTypeStatus;
	}
	public void setAccountTypeStatus(boolean accountTypeStatus) {
		this.accountTypeStatus = accountTypeStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
