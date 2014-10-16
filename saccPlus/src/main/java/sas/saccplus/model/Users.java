package sas.saccplus.model;

import javax.persistence.Entity;
import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import sas.saccplus.model.UserBranch;
import sas.saccplus.model.UserCompany;
import sas.saccplus.model.UserRole;

import java.lang.Override;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;

@Entity
public class Users implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8901871827462163162L;
	@Id
	private @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	Long id = null;
	@Version
	private @Column(name = "version")
	int version = 0;

	@Column
	private String userName;

	@Column
	private String userFullName;

	@Column
	private String userFullNameEn;

	@Column
	private String password;

	@Column
	private String email;

	@Column
	private boolean status;

	@Column
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date createDateTime;

	@Column
	private String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date updateDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastLoginDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastChangePwdDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column
	private Date lastResetPwdDateTime;

	@Column
	private String inActiveReason;

	@Column
	private boolean forceChangePwd;

	@Column
	private boolean expiredFlag;

	@Temporal(TemporalType.TIMESTAMP)
	private Date expireDate;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserRole> userRoles = new HashSet<UserRole>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserCompany> userCompanys = new HashSet<UserCompany>();

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<UserBranch> userBranchs = new HashSet<UserBranch>();

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
			return id.equals(((Users) that).id);
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

	public String getUserFullName()
	{
		return userFullName;
	}

	public void setUserFullName(String userFullName)
	{
		this.userFullName = userFullName;
	}

	public String getUserFullNameEn()
	{
		return userFullNameEn;
	}

	public void setUserFullNameEn(String userFullNameEn)
	{
		this.userFullNameEn = userFullNameEn;
	}

	public Date getCreateDateTime()
	{
		return createDateTime;
	}

	public void setCreateDateTime(Date createDateTime)
	{
		this.createDateTime = createDateTime;
	}

	public Date getUpdateDateTime()
	{
		return updateDateTime;
	}

	public void setUpdateDateTime(Date updateDateTime)
	{
		this.updateDateTime = updateDateTime;
	}

	public Date getLastChangePwdDateTime()
	{
		return lastChangePwdDateTime;
	}

	public void setLastChangePwdDateTime(Date lastChangePwdDateTime)
	{
		this.lastChangePwdDateTime = lastChangePwdDateTime;
	}

	public Date getLastResetPwdDateTime()
	{
		return lastResetPwdDateTime;
	}

	public void setLastResetPwdDateTime(Date lastResetPwdDateTime)
	{
		this.lastResetPwdDateTime = lastResetPwdDateTime;
	}

	public boolean isForceChangePwd()
	{
		return forceChangePwd;
	}

	public void setForceChangePwd(boolean forceChangePwd)
	{
		this.forceChangePwd = forceChangePwd;
	}

	public boolean isExpiredFlag()
	{
		return expiredFlag;
	}

	public void setExpiredFlag(boolean expiredFlag)
	{
		this.expiredFlag = expiredFlag;
	}

	public Date getLastLoginDateTime()
	{
		return lastLoginDateTime;
	}

	public void setLastLoginDateTime(Date lastLoginDateTime)
	{
		this.lastLoginDateTime = lastLoginDateTime;
	}

	public String getInActiveReason()
	{
		return inActiveReason;
	}

	public void setInActiveReason(String inActiveReason)
	{
		this.inActiveReason = inActiveReason;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getCreateBy()
	{
		return this.createBy;
	}

	public void setCreateBy(final String createBy)
	{
		this.createBy = createBy;
	}

	public String getUpdateBy()
	{
		return this.updateBy;
	}

	public void setUpdateBy(final String updateBy)
	{
		this.updateBy = updateBy;
	}

	public Date getExpireDate()
	{
		return this.expireDate;
	}

	public void setExpireDate(final Date expireDate)
	{
		this.expireDate = expireDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString()
	{
		String result = getClass().getSimpleName() + " ";
		result += "serialVersionUID: " + serialVersionUID;
		if (userName != null && !userName.trim().isEmpty())
			result += ", userName: " + userName;
		if (userFullName != null && !userFullName.trim().isEmpty())
			result += ", userFullName: " + userFullName;
		if (userFullNameEn != null && !userFullNameEn.trim().isEmpty())
			result += ", userFullNameEn: " + userFullNameEn;
		if (password != null && !password.trim().isEmpty())
			result += ", password: " + password;
		if (email != null && !email.trim().isEmpty())
			result += ", email: " + email;
		result += ", status: " + status;
		if (inActiveReason != null && !inActiveReason.trim().isEmpty())
			result += ", inActiveReason: " + inActiveReason;
		if (createBy != null && !createBy.trim().isEmpty())
			result += ", createBy: " + createBy;
		if (updateBy != null && !updateBy.trim().isEmpty())
			result += ", updateBy: " + updateBy;
		result += ", forceChangePwd: " + forceChangePwd;
		result += ", expiredFlag: " + expiredFlag;

		return result;
	}

	public Set<UserRole> getUserRoles()
	{
		return this.userRoles;
	}

	public void setUserRoles(final Set<UserRole> userRoles)
	{
		this.userRoles = userRoles;
	}

	public Set<UserCompany> getUserCompanys() {
		return userCompanys;
	}

	public void setUserCompanys(Set<UserCompany> userCompanys) {
		this.userCompanys = userCompanys;
	}

	public Set<UserBranch> getUserBranchs() {
		return userBranchs;
	}

	public void setUserBranchs(Set<UserBranch> userBranchs) {
		this.userBranchs = userBranchs;
	}

}