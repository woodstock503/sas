package sas.saccplus.dto;

import java.util.Date;

public class DTOAccountBook {
	private Long id;
	private String accountBookName;
	private String accountBookNameEn;
	private String description;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private boolean accountBookStatus;
	private String accountBookCode;


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAccountBookName() {
		return accountBookName;
	}
	public void setAccountBookName(String accountBookName) {
		this.accountBookName = accountBookName;
	}
	public String getAccountBookNameEn() {
		return accountBookNameEn;
	}
	public void setAccountBookNameEn(String accountBookNameEn) {
		this.accountBookNameEn = accountBookNameEn;
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
	public boolean isAccountBookStatus() {
		return accountBookStatus;
	}
	public void setAccountBookStatus(boolean accountBookStatus) {
		this.accountBookStatus = accountBookStatus;
	}
	public String getAccountBookCode() {
		return accountBookCode;
	}
	public void setAccountBookCode(String accountBookCode) {
		this.accountBookCode = accountBookCode;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		if (accountBookName != null && !accountBookName.trim().isEmpty())
			result += "accountBookName: " + accountBookName;
		if (accountBookNameEn != null && !accountBookNameEn.trim().isEmpty())
			result += ", accountBookNameEn: " + accountBookNameEn;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		if (createBy != null && !createBy.trim().isEmpty())
			result += ", createBy: " + createBy;
		if (updateBy != null && !updateBy.trim().isEmpty())
			result += ", updateBy: " + updateBy;
		result += ", accountBookStatus: " + accountBookStatus;
		if (accountBookCode != null && !accountBookCode.trim().isEmpty())
			result += ", accountBookCode: " + accountBookCode;
		return result;
	}
}
