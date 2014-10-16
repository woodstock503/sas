package sas.saccplus.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import sas.saccplus.model.Branch;
import sas.saccplus.model.Company;
import sas.saccplus.model.Department;
import sas.saccplus.model.Users;
import sas.saccplus.session.BranchFacade;
import sas.saccplus.session.CompanyFacade;
import sas.saccplus.view.LanguageBean;


@Named
@SessionScoped
public class SessionBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8185878929115837596L;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Inject
	private Conversation conversation;

	@Inject
	private LanguageBean languageBean;

	@EJB
	private CompanyFacade companyFacade;

	@EJB
	private BranchFacade branchFacade;

	private List<Company> companyList = new ArrayList<Company>();
	private List<Branch> branchList = new ArrayList<Branch>();
	private List<Department> departmentList = new ArrayList<Department>();

	private Users user;
	private PackageType packageType;
	private Company company = new Company();
	private Branch branch = new Branch();

	private String skinName;

	@PostConstruct
	public void init(){
		skinName = "deepMarine";
	}

	public String changeSkin(String skinName){
		this.skinName = skinName;
		return "?faces-redirect=true";
	}

	public boolean isLocal() {
		return !"en".equals(languageBean.getLocaleCode());
	}

	public String selectedCompany(Company company){
		try {
			this.company = company;
			genDepartmentListByCompany();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index?faces-redirect=true";
	}

	public String selectedBranch(Branch branch){
		try {
			this.branch = branch;
			genDepartmentListByCompany();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "index?faces-redirect=true";
	}

	public void changeCompany(ValueChangeEvent event){
		try {
			if (!this.conversation.isTransient())
			{
				this.conversation.end();
			}
			Long companyId =(Long) event.getNewValue();
			company = companyFacade.findById(companyId);
			genDepartmentListByCompany();
			if(PackageType.ALL.equals(packageType)){
				genBranchListByCompany(companyId);
				if(branchList!=null&&!branchList.isEmpty()){
					branch = branchList.get(0);
				}else{
					branch = new Branch();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void changeBranch(ValueChangeEvent event){
		try {
			if (!this.conversation.isTransient())
			{
				this.conversation.end();
			}
			branch = branchFacade.findById((Long) event.getNewValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String formatDate(Date date,String pattern){
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public boolean showCompanyList(){
		return PackageType.PROFESSIONAL.equals(packageType)||PackageType.ALL.equals(packageType);
	}

	public boolean showCompanyLabel(){
		return PackageType.STANDARD.equals(packageType)||PackageType.PROFESSIONAL.equals(packageType)||PackageType.ALL.equals(packageType);
	}

	public boolean showBranch(){
		return PackageType.ENTERPRISE.equals(packageType)||PackageType.ALL.equals(packageType);
	}



	@SuppressWarnings("unchecked")
	private void genDepartmentListByCompany() throws Exception{
		try{
			departmentList = entityManager.createQuery("From Department where company.id=:companyId").setParameter("companyId", company.getId()).getResultList();
			Department dep = new Department();
			dep.setId(-1L);
			dep.setDepartmentName("ทั้งหมด");
			dep.setDepartmentNameEn("All");
			departmentList.add(0,dep);
		}catch(Exception ex){
			throw ex;
		}
	}


	private void genBranchListByCompany(Long companyId) throws Exception{
		try{
			branchList = branchFacade.findBranchByUser(user,companyId);
		}catch(Exception ex){
			throw ex;
		}
	}


	public void paint(OutputStream stream, Object object) throws IOException {
		try{
			if(getCompany().getLogo()!=null){
				stream.write(getCompany().getLogo());
				stream.close();
			}
		}catch(Exception ex){
//			ex.printStackTrace();
		}
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users userProfile) {
		this.user = userProfile;
	}

	public String getSkinName() {
		return skinName;
	}

	public void setSkinName(String skinName) {
		this.skinName = skinName;
	}

	public List<Company> getCompanyList() {
		return companyList;
	}

	public void setCompanyList(List<Company> companyList) {
		this.companyList = companyList;
	}

	public List<Branch> getBranchList() {
		return branchList;
	}

	public void setBranchList(List<Branch> branchList) {
		this.branchList = branchList;
	}

	public PackageType getPackageType() {
		return packageType;
	}

	public void setPackageType(PackageType packageType) {
		this.packageType = packageType;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Branch getBranch() {
		return branch;
	}

	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	
	private String value;
	
	public void updateValue(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
