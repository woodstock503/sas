package sas.saccplus.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sas.saccplus.model.Branch;
import sas.saccplus.model.Company;
import sas.saccplus.model.Role;
import sas.saccplus.model.UserBranch;
import sas.saccplus.model.UserCompany;
import sas.saccplus.model.UserRole;

public class DTOUserProfile {

	private Long id;
	private int version;
	private String userLoginName;
	private String userFullName;
	private String userFullNameEn;
	private String userPassword;
	private String email;
	private boolean userStatus;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private Date lastLoginDateTime;
	private Date lastChangePwdDateTime;
	private Date lastResetPwdDateTime;
	private String inActiveReason;
	private boolean forceChangePwd;
	private boolean expiredFlag;
	private Date expireDate;
	private Set<UserRole> userRoles = new HashSet<UserRole>();
	private Set<UserCompany> userCompanys = new HashSet<UserCompany>();
	private Set<UserBranch> userBranchs = new HashSet<UserBranch>();
	
	
	/** criteria **/
	private List<Role> roles = new ArrayList<Role>();
	private List<Company> companys = new ArrayList<Company>();
	private List<Branch> Branchs = new ArrayList<Branch>();
	

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersion() {
		return version;
	}
	public void setVersion(int version) {
		this.version = version;
	}
	public String getUserLoginName() {
		return userLoginName;
	}
	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	public String getUserFullName() {
		return userFullName;
	}
	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	public String getUserFullNameEn() {
		return userFullNameEn;
	}
	public void setUserFullNameEn(String userFullNameEn) {
		this.userFullNameEn = userFullNameEn;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean isUserStatus() {
		return userStatus;
	}
	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
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
	public Date getLastLoginDateTime() {
		return lastLoginDateTime;
	}
	public void setLastLoginDateTime(Date lastLoginDateTime) {
		this.lastLoginDateTime = lastLoginDateTime;
	}
	public Date getLastChangePwdDateTime() {
		return lastChangePwdDateTime;
	}
	public void setLastChangePwdDateTime(Date lastChangePwdDateTime) {
		this.lastChangePwdDateTime = lastChangePwdDateTime;
	}
	public Date getLastResetPwdDateTime() {
		return lastResetPwdDateTime;
	}
	public void setLastResetPwdDateTime(Date lastResetPwdDateTime) {
		this.lastResetPwdDateTime = lastResetPwdDateTime;
	}
	public String getInActiveReason() {
		return inActiveReason;
	}
	public void setInActiveReason(String inActiveReason) {
		this.inActiveReason = inActiveReason;
	}
	public boolean isForceChangePwd() {
		return forceChangePwd;
	}
	public void setForceChangePwd(boolean forceChangePwd) {
		this.forceChangePwd = forceChangePwd;
	}
	public boolean isExpiredFlag() {
		return expiredFlag;
	}
	public void setExpiredFlag(boolean expiredFlag) {
		this.expiredFlag = expiredFlag;
	}
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
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
	public List<Company> getCompanys() {
		return companys;
	}
	public void setCompanys(List<Company> companys) {
		this.companys = companys;
	}
	public List<Branch> getBranchs() {
		return Branchs;
	}
	public void setBranchs(List<Branch> branchs) {
		Branchs = branchs;
	}
}
