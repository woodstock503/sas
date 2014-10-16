package sas.saccplus.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sas.saccplus.model.ProgramRole;
import sas.saccplus.model.Role;

public class DTOProgram {

	private Long id;
	private int version;
	private String programName;
	private String programNameEn;
	private boolean programStatus;
	private String url;
	private String description;
	private String createBy;
	private Date createDateTime;
	private String updateBy;
	private Date updateDateTime;
	private Set<ProgramRole> programRoles = new HashSet<ProgramRole>();
	
	
	/** criteria **/
	private List<Role> roles = new ArrayList<Role>();


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
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramNameEn() {
		return programNameEn;
	}
	public void setProgramNameEn(String programNameEn) {
		this.programNameEn = programNameEn;
	}
	public boolean isProgramStatus() {
		return programStatus;
	}
	public void setProgramStatus(boolean programStatus) {
		this.programStatus = programStatus;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public Set<ProgramRole> getProgramRoles() {
		return programRoles;
	}
	public void setProgramRoles(Set<ProgramRole> programRoles) {
		this.programRoles = programRoles;
	}

}
