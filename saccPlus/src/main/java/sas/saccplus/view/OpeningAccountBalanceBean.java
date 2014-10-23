package sas.saccplus.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

//import org.richfaces.application.ServiceTracker;
//import org.richfaces.focus.FocusManager;

import sas.saccplus.model.AccountBalance;
import sas.saccplus.model.ChartOfAccount;
import sas.saccplus.session.AccountBalanceFacade;
import sas.saccplus.session.AccountYearFacade;
import sas.saccplus.session.ChartOfAccountFacade;
import sas.saccplus.session.CompanyFacade;
import sas.saccplus.util.BeanUtil;
import sas.saccplus.util.SessionBean;




@Named
@ConversationScoped
public class OpeningAccountBalanceBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8131577460040967220L;


	@Inject
	private Conversation conversation;
	@Inject
	private SessionBean session;
	@Inject 
	private BeanUtil beanUtil;
	
	@EJB
	private CompanyFacade companyFacade;
	@EJB
	private AccountBalanceFacade accountBalanceFacade;
	@EJB
	private ChartOfAccountFacade chartOfAccountFacade;
	@EJB
	private AccountYearFacade accountYearFacade;


	private List<ChartOfAccount> chartOfAccountList;
	private List<AccountBalance> accountBalanceList;


	private BigDecimal totalLoAmtDebit;
	private BigDecimal totalLoAmtCredit;
	private BigDecimal differenceLoAmount;
	private Date balanceDate;



	@PostConstruct
	public void initData(){
		if (this.conversation.isTransient())
		{
			this.conversation.begin();
		}
		try{

			if(session.getCompany()==null){
				throw new Exception("company is null");
			}
			clearData();
			accountBalanceList = accountBalanceFacade.findBeginingBalance(session.getCompany().getId());
			if(accountBalanceList.size()>0){
				balanceDate = accountBalanceList.get(0).getOpeningAccountYear().getStartDate();
			}
			
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
			ex.printStackTrace();
		}
	}

	
	public void create(){
		try{
			chartOfAccountList = chartOfAccountFacade.findRootChartOfAccount(session.getCompany().getId());
			accountBalanceList = new ArrayList<AccountBalance>();
			for(ChartOfAccount c:chartOfAccountList){
				AccountBalance accountBalance = new AccountBalance();
				accountBalance.setChartOfAccount(c);
				accountBalance.setCompany(c.getCompany());
				accountBalance.setBeginBalance(true);
				accountBalance.setCreditBalance(new BigDecimal(0));
				accountBalance.setDebitBalance(new BigDecimal(0));
				accountBalance.setDescription(c.getChartOfAccountName());
				accountBalance.setDescriptionEn(c.getChartOfAccountNameEn());
				accountBalanceList.add(accountBalance);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void clearData(){
		balanceDate = new Date();
		chartOfAccountList = new ArrayList<ChartOfAccount>();
		accountBalanceList = new ArrayList<AccountBalance>();
		totalLoAmtDebit = new BigDecimal(0);
		totalLoAmtCredit = new BigDecimal(0);
		differenceLoAmount = new BigDecimal(0);
	}

	public void calculateLoAmountDebit(Integer index){
		try{
			updateData();
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
			ex.printStackTrace();
		}
	}

	public void calculateLoAmountCredit(Integer index){
		try{
			updateData();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public String save(){
		String result = "Save Completed.";
		try{
			if(!totalLoAmtCredit.equals(totalLoAmtDebit)){
				result = "It can not save becuase Debit and Credit is not equal";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, beanUtil.getMessage("transaction_save",result),null));
				return null;
			}
			
			accountBalanceFacade.saveOrUpdateChartOfAccount(accountBalanceList,balanceDate,session.getCompany(), session.getUser());
			clearData();

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, beanUtil.getMessage("transaction_save_complete",result),null));
			conversation.end();
			return "openingAccountBalance?faces-redirect=true";
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, beanUtil.getMessage("journalTransaction_error",ex.getMessage()),null));
			ex.printStackTrace();
		}
		return null;
	}


	private void updateData() throws Exception{
		try{
			totalLoAmtDebit = new BigDecimal(0);
			totalLoAmtCredit = new BigDecimal(0);
			for(AccountBalance model: accountBalanceList){
				totalLoAmtDebit = totalLoAmtDebit.add(model.getDebitBalance());
				totalLoAmtCredit = totalLoAmtCredit.add(model.getCreditBalance());
			}
			differenceLoAmount=totalLoAmtDebit.subtract(totalLoAmtCredit);
		}catch(Exception e){
			throw e;
		}
	}
	
	



	/** manage tab index**/

//	public void tabToLoAmountCredit(){
//		try{
//			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
//			focusManager.focus("loAmountCredit");
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}
//	}
	/** manage tab index**/

	public List<AccountBalance> getAccountBalanceList() {
		return accountBalanceList;
	}

	public void setAccountBalanceList(List<AccountBalance> accountBalanceList) {
		this.accountBalanceList = accountBalanceList;
	}

	public BigDecimal getTotalLoAmtDebit() {
		return totalLoAmtDebit;
	}

	public void setTotalLoAmtDebit(BigDecimal totalLoAmtDebit) {
		this.totalLoAmtDebit = totalLoAmtDebit;
	}

	public BigDecimal getTotalLoAmtCredit() {
		return totalLoAmtCredit;
	}

	public void setTotalLoAmtCredit(BigDecimal totalLoAmtCredit) {
		this.totalLoAmtCredit = totalLoAmtCredit;
	}

	public BigDecimal getDifferenceLoAmount() {
		return differenceLoAmount;
	}

	public void setDifferenceLoAmount(BigDecimal differenceLoAmount) {
		this.differenceLoAmount = differenceLoAmount;
	}

	public Date getBalanceDate() {
		return balanceDate;
	}

	public void setBalanceDate(Date balanceDate) {
		this.balanceDate = balanceDate;
	}

}
