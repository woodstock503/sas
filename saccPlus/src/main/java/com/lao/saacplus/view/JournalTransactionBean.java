package com.lao.saacplus.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.CellEditEvent;
import org.richfaces.application.ServiceTracker;
import org.richfaces.focus.FocusManager;

import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.dto.DTOTransactionDetail;
import com.lao.saacplus.dto.DTOTransactionMaster;
import com.lao.saacplus.entity.AccountBook;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.Department;
import com.lao.saacplus.entity.Rate;
import com.lao.saacplus.entity.TransactionDetail;
import com.lao.saacplus.session.AccountBookFacade;
import com.lao.saacplus.session.BranchFacade;
import com.lao.saacplus.session.ChartOfAccountFacade;
import com.lao.saacplus.session.CompanyFacade;
import com.lao.saacplus.session.CounterSingleton;
import com.lao.saacplus.session.DepartmentFacade;
import com.lao.saacplus.session.RateFacade;
import com.lao.saacplus.session.TransactionDetailFacade;
import com.lao.saacplus.session.TransactionMasterFacade;
import com.lao.saacplus.util.BeanUtil;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;




@Named
@ConversationScoped
public class JournalTransactionBean implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = 2405273876985220856L;

	@Inject
	private Conversation conversation;

	@Inject
	private SessionBean session;

	@Inject 
	private BeanUtil beanUtil;



	@EJB
	private CompanyFacade companyFacade;
	@EJB
	private BranchFacade branchFacade;
	@EJB
	private AccountBookFacade accountBookFacade;
	@EJB
	private RateFacade rateFacade;
	@EJB
	private ChartOfAccountFacade chartOfAccountFacade;
	@EJB
	private DepartmentFacade departmentFacade;
	@EJB
	private CounterSingleton counterSingleton;


	@EJB
	private TransactionMasterFacade transactionMasterFacade;
	@EJB
	private TransactionDetailFacade transactionDetailFacade;


	private final int CAL_STANDARD = 1;
	private final int CAL_SPECIAL = 2;


	private DTOTransactionMaster transactionMaster;
	private List<DTOTransactionDetail> transactionList;
	private List<AccountBook> accountBookList;
	private List<ChartOfAccount> chartOfAccountList;
	private List<Department> departmentList;
	private List<Rate> rateList;

	private DTOTransactionDetail selectedDelete;
	private DTOChartOfAccount selectedChartOfAccount;


	private BigDecimal differenceAmount;
	private BigDecimal differenceAmountLo;


	private Long id;
	private String redirectPage = "journalTransaction?faces-redirect=true";


	@PostConstruct
	public void initJournalTransaction(){
		initData();
	}

	public void retrieve()
	{
		try{

			if (FacesContext.getCurrentInstance().isPostback())
			{
				return;
			}
			if (this.conversation.isTransient())
			{
				this.conversation.begin();
			}

			if (this.id != null)
			{
				this.transactionMaster = ConverterDTO.transactionMaster(transactionMasterFacade.findById(id));
				if(transactionMaster!=null){
					List<TransactionDetail> details = transactionDetailFacade.findTransactionDetailByTransactionMaster(transactionMaster.getId());
					transactionList = new ArrayList<DTOTransactionDetail>();
					for(TransactionDetail d:details){
						transactionList.add(ConverterDTO.transactionDetail(d));
					}
				}
				redirectPage = "/view/transactionMaster/search?faces-redirect=true";
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void initData(){
		try{

			if(session.getCompany()==null){
				throw new Exception("company is null");
			}

			clearData();
			accountBookList = accountBookFacade.findAccountBook();
			rateList = rateFacade.findRate();

			chartOfAccountList = chartOfAccountFacade.findRootChartOfAccount(session.getCompany().getId());
			for(ChartOfAccount c:chartOfAccountFacade.findInactiveChartOfAccount(session.getCompany().getId())){
				chartOfAccountList.add(c);
			}

			departmentList = departmentFacade.findDepartmentByCompanyId(session.getCompany().getId());
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
			ex.printStackTrace();
		}
	}

	public void clearData(){
		transactionMaster = new DTOTransactionMaster();
		transactionList = new ArrayList<DTOTransactionDetail>();
		transactionMaster.setBillDate(new Date());
		transactionMaster.setTotalAmountDebit(new BigDecimal(0));
		transactionMaster.setTotalAmountCredit(new BigDecimal(0));
		transactionMaster.setTotalAmountDebitLo(new BigDecimal(0));
		transactionMaster.setTotalAmountCreditLo(new BigDecimal(0));

		differenceAmount = new BigDecimal(0);
		differenceAmountLo = new BigDecimal(0);
	}

	public void addTransaction(){
		DTOTransactionDetail model = new DTOTransactionDetail();
		transactionList.add(model);
	}

	public void deleteTransaction(){
		try{
			transactionList.remove(selectedDelete);
			updateData(CAL_STANDARD);
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
			ex.printStackTrace();
		}
	}


	public void calculateRate(Integer index){
		calculate(index);
	}


	public void calculate(Integer index){
		try{
			updateData(CAL_STANDARD);
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
			ex.printStackTrace();
		}
	}


	public void calculateLoAmountDebit(Integer index){
		try{
			DTOTransactionDetail model = transactionList.get(index);
			BigDecimal loAmountDebitRate = model.getAmountDebitLo().divide(model.getAmountDebit(),beanUtil.digitScale(),RoundingMode.HALF_UP);
			model.setTransactionRate(loAmountDebitRate);
			updateData(CAL_SPECIAL);
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
			ex.printStackTrace();
		}
	}

	public void calculateLoAmountCredit(Integer index){
		try{
			DTOTransactionDetail model = transactionList.get(index);
			BigDecimal loAmountCreditRate = model.getAmountCreditLo().divide(model.getAmountCredit(),beanUtil.digitScale(),RoundingMode.HALF_UP);
			model.setTransactionRate(loAmountCreditRate);
			updateData(CAL_SPECIAL);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void changeRate(Integer index){
		try{
			DTOTransactionDetail model = transactionList.get(index);
			model.setTransactionRate(model.getRate().getRate());
			updateData(CAL_STANDARD);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	public void selectAccountCode(Integer index){
		
		System.out.println("===================>"+index);
		
		try{
			
			DTOTransactionDetail model = transactionList.get(index.intValue());
			ChartOfAccount chart = model.getChartOfAccount();
			
			
			model.setAccountType(chart.getAccountType());
			model.setDescription(chart.getChartOfAccountName());
			model.setDescriptionEn(chart.getChartOfAccountNameEn());
			if(chart.getCurrency().getId()!=null&&chart.getCurrency().getId().longValue()>0){
				Rate dotRate = rateFacade.findCurrentRateByCurrency(chart.getCurrency().getId());
				model.setRate(dotRate);
				model.setTransactionRate(dotRate.getRate());
			}

			/*------- wait for auto value next record ---------*/
			/*
			if(index.intValue()==0){
				model.setDisableDebit(false);
				model.setDisableCredit(true);
			}else{
				JournalTransactionModel modelPrev = transactionList.get(index-1);
				BigDecimal debitPrev = beanUtil.convertStringToBigDecimal(modelPrev.getAmountDebit());
				BigDecimal creditPrev = beanUtil.convertStringToBigDecimal(modelPrev.getAmountCredit());
				BigDecimal debitTotal = beanUtil.convertStringToBigDecimal(totalAmountDebit);
				BigDecimal creditTotal = beanUtil.convertStringToBigDecimal(totalAmountCredit);
				if(beanUtil.convertStringToNumber(creditPrev.toString()).doubleValue()>0){
					model.setDisableDebit(false);
					model.setDisableCredit(true);
					//					creditTotal = beanUtil.convertStringToBigDecimal(amount).subtract(debitTotal);
					model.setAmountDebit(beanUtil.convertNumber(creditTotal.toString()));
				}else if(beanUtil.convertStringToNumber(debitPrev.toString()).doubleValue()>0){
					model.setDisableDebit(true);
					model.setDisableCredit(false);
					//					debitTotal = beanUtil.convertStringToBigDecimal(amount).subtract(creditTotal);
					model.setAmountCredit(beanUtil.convertNumber(debitTotal.toString()));
				}else{
					model.setDisableDebit(false);
					model.setDisableCredit(false);
				}
			}
			 */
			/*------- wait for auto value next record ---------*/
			updateData(CAL_STANDARD);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String save(){
		String result = "Save Completed.";
		try{

			if(transactionMaster.getCertifyNo()==null||"".equals(transactionMaster.getCertifyNo().trim())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, beanUtil.getMessage("transaction_save","It can not save becuase Certify No. is require"),null));
				return null;
			}

			if(!transactionMaster.getTotalAmountCreditLo().equals(transactionMaster.getTotalAmountDebitLo())){
				result = "It can not save becuase Debit and Credit is not equal";
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, beanUtil.getMessage("transaction_save",result),null));
				return null;
			}

			if(!checkHaveItems()){
				/** list size > 0 but data is null **/
				return null;
			}



			if(transactionMaster.getId()==null){
				transactionMaster.setCreateBy(session.getUserProfile().getUserLoginName());
				transactionMaster.setCreateDateTime(new Date());
				transactionMaster.setCreateUser(session.getUserProfile());
				transactionMaster.setTransactionMasterStatus(true);
				transactionMaster.setBranch(branchFacade.findById(session.getBranch().getId()));
				transactionMaster.setCompany(companyFacade.findById(session.getCompany().getId()));
				transactionMasterFacade.saveNewJournalTransactionMaster(transactionMaster,transactionList);
				clearData();

			}else{
				transactionMaster.setUpdateBy(session.getUserProfile().getUserLoginName());
				transactionMaster.setUpdateDateTime(new Date());
				transactionMasterFacade.updateNewJournalTransactionMaster(transactionMaster,transactionList);
				clearData();
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, beanUtil.getMessage("transaction_save_complete",result),null));
			conversation.end();
			return redirectPage;


		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, beanUtil.getMessage("journalTransaction_error",ex.getMessage()),null));
			ex.printStackTrace();
		}
		return null;
	}


	private void updateData(int updateType) throws Exception{
		try{
			BigDecimal totalAmountDebit = new BigDecimal("0");
			BigDecimal totalAmountCredit = new BigDecimal("0");
			BigDecimal totalAmountDebitLo = new BigDecimal("0");
			BigDecimal totalAmountCreditLo = new BigDecimal("0");
			for(DTOTransactionDetail model: transactionList){
				BigDecimal amountDebit = model.getAmountDebit();
				BigDecimal amountCredit = model.getAmountCredit();
				BigDecimal rate = model.getTransactionRate();
				if(CAL_STANDARD==updateType){
					model.setAmountCreditLo(amountCredit.multiply(rate));
					model.setAmountDebitLo(amountDebit.multiply(rate));
				}
				totalAmountDebit = totalAmountDebit.add(amountDebit);
				totalAmountCredit = totalAmountCredit.add(amountCredit);
				totalAmountDebitLo = totalAmountDebitLo.add(amountDebit.multiply(rate));
				totalAmountCreditLo = totalAmountCreditLo.add(amountCredit.multiply(rate));
			}
			transactionMaster.setTotalAmountCredit(totalAmountCredit);
			transactionMaster.setTotalAmountCreditLo(totalAmountCreditLo);
			transactionMaster.setTotalAmountDebit(totalAmountDebit);
			transactionMaster.setTotalAmountDebitLo(totalAmountDebitLo);

			totalAmountDebit = totalAmountDebit.subtract(totalAmountCredit);
			totalAmountDebitLo = totalAmountDebitLo.subtract(totalAmountCreditLo);
			this.differenceAmount = totalAmountDebit;
			this.differenceAmountLo = totalAmountDebitLo;
		}catch(Exception e){
			throw e;
		}
	}

	public void changeAccountBook(ValueChangeEvent e){
		try{
			//			selectedAccountBook = (AccountBook) e.getNewValue();
			generateCertifyNo();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void generateCertifyNo() throws Exception{
		try{
			StringBuffer certifyNo = new StringBuffer();
			if(transactionMaster.getAccountBook()!=null){
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd-",Locale.US);
				String dateStr = sdf.format(new Date());
				certifyNo.append(transactionMaster.getAccountBook().getAccountBookCode());
				certifyNo.append(dateStr);
				certifyNo.append(counterSingleton.getCounter());
			}
			transactionMaster.setCertifyNo(certifyNo.toString());
		}catch(Exception ex){
			throw ex;
		}
	}

	public void onCellEdit(CellEditEvent event) {  
		Object oldValue = event.getOldValue();  
		Object newValue = event.getNewValue();  

		if(newValue != null && !newValue.equals(oldValue)) {  
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);  
			FacesContext.getCurrentInstance().addMessage(null, msg);  
		}  
	}  


	private boolean checkHaveItems(){
		for(DTOTransactionDetail t:transactionList){
			if(t.getChartOfAccount()!=null){
				return true;
			}
		}
		return false;
	}


	public List<ChartOfAccount> complete(String query) {  
		List<ChartOfAccount> filterList = new ArrayList<ChartOfAccount>();

		for(ChartOfAccount a:chartOfAccountList){
			if(a.getChartOfAccountCode().contains(query)){
				filterList.add(a);
			}
		}
		return  filterList;  
	}  

	/** manage tab index**/
	public void tabToAccountCodeList() {
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("accountCodeList");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToDepartmentList() {
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("departmentList");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToLoName(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("loName");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToAmountDebit(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("amountDebit");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToAmountCredit(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("amountCredit");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToCurrency(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("currency");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToRate(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("rate");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToLoAmountDebit(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("loAmountDebit");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToLoAmountCredit(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("loAmountCredit");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToAddTransaction(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("addTransaction");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void tabToDescription(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("description");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void tabToRemove(){
		try{
			FocusManager focusManager = ServiceTracker.getService(FocusManager.class);
			focusManager.focus("remove");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	/** manage tab index**/

	public List<DTOTransactionDetail> getTransactionList() {
		return transactionList;
	}

	public void setTransactionList(List<DTOTransactionDetail> transactionList) {
		this.transactionList = transactionList;
	}

	public DTOTransactionDetail getSelectedDelete() {
		return selectedDelete;
	}

	public void setSelectedDelete(DTOTransactionDetail selectedDelete) {
		this.selectedDelete = selectedDelete;
	}

	public List<ChartOfAccount> getChartOfAccountList() {
		return chartOfAccountList;
	}

	public void setChartOfAccountList(List<ChartOfAccount> chartOfAccountList) {
		this.chartOfAccountList = chartOfAccountList;
	}

	public DTOChartOfAccount getSelectedChartOfAccount() {
		return selectedChartOfAccount;
	}

	public void setSelectedChartOfAccount(DTOChartOfAccount selectedChartOfAccount) {
		this.selectedChartOfAccount = selectedChartOfAccount;
	}

	public BigDecimal getDifferenceAmount() {
		return differenceAmount;
	}

	public void setDifferenceAmount(BigDecimal differenceAmount) {
		this.differenceAmount = differenceAmount;
	}

	public BigDecimal getDifferenceAmountLo() {
		return differenceAmountLo;
	}

	public void setDifferenceAmountLo(BigDecimal differenceAmountLo) {
		this.differenceAmountLo = differenceAmountLo;
	}

	public List<AccountBook> getAccountBookList() {
		return accountBookList;
	}

	public void setAccountBookList(List<AccountBook> accountBookList) {
		this.accountBookList = accountBookList;
	}

	public List<Rate> getRateList() {
		return rateList;
	}

	public void setRateList(List<Rate> rateList) {
		this.rateList = rateList;
	}

	public List<Department> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<Department> departmentList) {
		this.departmentList = departmentList;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DTOTransactionMaster getTransactionMaster() {
		return transactionMaster;
	}

	public void setTransactionMaster(DTOTransactionMaster transactionMaster) {
		this.transactionMaster = transactionMaster;
	}

}
