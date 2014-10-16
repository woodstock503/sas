package com.lao.saacplus.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.lao.saacplus.dto.DTOAccountBalance;
import com.lao.saacplus.dto.DTOAccountYear;
import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.dto.DTOTransactionDetail;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.TransactionDetail;
import com.lao.saacplus.session.AccountBalanceFacade;
import com.lao.saacplus.session.AccountYearFacade;
import com.lao.saacplus.session.ChartOfAccountFacade;
import com.lao.saacplus.session.CompanyFacade;
import com.lao.saacplus.session.TransactionDetailFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;
import com.lao.saacplus.util.StringUtil;

/**
 * Backing bean for AccountYear entities.
 * <p>
 * This class provides CRUD functionality for all AccountYear entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class AccountYearBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving AccountYear entities
	 */

	@Inject
	private Conversation conversation;
	@Inject
	private SessionBean session;


	@EJB
	private CompanyFacade companyFacade;
	@EJB
	private AccountYearFacade accountYearFacade;
	@EJB
	private ChartOfAccountFacade chartOfAccountFacade;
	@EJB
	private AccountBalanceFacade accountBalanceFacade;
	@EJB
	private TransactionDetailFacade transactionDetailFacade;


	private DTOAccountYear accountYear;
	private Long id;
	private int page;
	private long count;
	private List<AccountYear> pageItems;

	private DTOAccountYear example = new DTOAccountYear();


	public void closeAccountYear(){
		try{

			/**get root chartOfAccount**/
			List<ChartOfAccount> chList = chartOfAccountFacade.findRootChartOfAccount(session.getCompany().getId());

			List<DTOChartOfAccount> rootChart = new ArrayList<DTOChartOfAccount>();
			for(ChartOfAccount c:chList){
				rootChart.add(ConverterDTO.chartOfAccount(c));
			}

			Calendar nextAccountYearDate = Calendar.getInstance();
			nextAccountYearDate.setTime(accountYear.getEndDate());
			nextAccountYearDate.add(Calendar.DAY_OF_MONTH, 1);
			nextAccountYearDate.set(Calendar.HOUR_OF_DAY, 0);
			nextAccountYearDate.set(Calendar.MINUTE, 0);
			nextAccountYearDate.set(Calendar.SECOND, 0);
			nextAccountYearDate.set(Calendar.MILLISECOND, 0);
			AccountYear nextAccountYear = accountYearFacade.findAccountYear(nextAccountYearDate.getTime(),session.getCompany(),session.getUserProfile());
			AccountYear previousYear = accountYearFacade.findPreviousAccountYear(accountYear.getEndDate(),session.getCompany().getId());


			BigDecimal debitBalance = new BigDecimal("0");
			BigDecimal creditBalance = new BigDecimal("0");
			List<DTOAccountBalance> accountBalances = new ArrayList<DTOAccountBalance>();
			for(DTOChartOfAccount chart:rootChart){

				List<DTOChartOfAccount> siblingChartList = new ArrayList<DTOChartOfAccount>();
				findChartSibling(chart,siblingChartList);

				List<DTOTransactionDetail> transactionDetailList = new ArrayList<DTOTransactionDetail>();
				for(TransactionDetail d:transactionDetailFacade.findTransactionDetail(siblingChartList,accountYear.getId())){
					transactionDetailList.add(ConverterDTO.transactionDetail(d));
				}

				BigDecimal amountDebitLo = new BigDecimal("0");
				BigDecimal amountCreditLo = new BigDecimal("0");

				for(DTOTransactionDetail detail:transactionDetailList){
					amountDebitLo = amountDebitLo.add(detail.getAmountDebitLo());
					amountCreditLo = amountCreditLo.add(detail.getAmountCreditLo());
				}

				DTOAccountBalance accountBalance = new DTOAccountBalance();
				accountBalance.setBeginBalance(false);
				accountBalance.setChartOfAccount(chartOfAccountFacade.findById(chart.getId()));
				accountBalance.setCompany(companyFacade.findById(session.getCompany().getId()));
				accountBalance.setDescription(chart.getChartOfAccountName());
				accountBalance.setDescriptionEn(chart.getChartOfAccountNameEn());
				accountBalance.setBalanceAccountYear(accountYearFacade.findById(accountYear.getId()));
				accountBalance.setOpeningAccountYear(accountYearFacade.findById(nextAccountYear.getId()));


				BigDecimal openingDr = new BigDecimal("0");
				BigDecimal openingCr = new BigDecimal("0");
				if(previousYear!=null){
					openingCr = StringUtil.n2b(previousYear.getCreditBalance());
					openingDr = StringUtil.n2b(previousYear.getDebitBalance());
				}
				if(chart.getAccountType().getAccountTypeStatus()){
					/**Opening Dr + Transaction Dr  - Transaction Cr**/
					openingDr = openingDr.add(StringUtil.n2b(amountDebitLo)).subtract(StringUtil.n2b(amountCreditLo));
					if(amountDebitLo.compareTo(amountCreditLo)>0){
						accountBalance.setCreditBalance(new BigDecimal("0"));
						accountBalance.setDebitBalance(openingDr);
					}else{
						accountBalance.setCreditBalance(openingDr);
						accountBalance.setDebitBalance(new BigDecimal("0"));
					}

				}else{
					/**Opening Cr + Transaction Cr  - Transaction Dr**/
					openingCr = openingCr.add(StringUtil.n2b(amountCreditLo)).subtract(StringUtil.n2b(amountDebitLo));
					if(amountCreditLo.compareTo(amountDebitLo)>0){
						accountBalance.setCreditBalance(new BigDecimal("0"));
						accountBalance.setDebitBalance(openingCr);
					}else{
						accountBalance.setCreditBalance(openingCr);
						accountBalance.setDebitBalance(new BigDecimal("0"));
					}
				}

				debitBalance = debitBalance.add(accountBalance.getDebitBalance());
				creditBalance = creditBalance.add(accountBalance.getCreditBalance());
				accountBalances.add(accountBalance);
			}
			accountYear.setCloseStatus(true);
			accountYear.setUpdateBy(session.getUserProfile().getUserLoginName());
			accountYear.setUpdateDateTime(new Date());
			accountYear.setDebitBalance(debitBalance);
			accountYear.setCreditBalance(creditBalance);
			accountYearFacade.closeAccountYear(accountYear,accountBalances);

			if (!this.conversation.isTransient())
			{
				this.conversation.end();
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	private void findChartSibling(DTOChartOfAccount root,List<DTOChartOfAccount> siblingChartList){
		siblingChartList.add(root);
		List<DTOChartOfAccount> chartList = findUnderChartOfAccount(root.getChartOfAccountCode());
		for(DTOChartOfAccount chart:chartList){
			findChartSibling(chart,siblingChartList);
		}
	}


	private List<DTOChartOfAccount> findUnderChartOfAccount(String code){
		List<DTOChartOfAccount> chartList = new ArrayList<DTOChartOfAccount>();;
		try{
			for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(code, session.getCompany().getId())){
				chartList.add(ConverterDTO.chartOfAccount(c));
			} 
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return chartList;
	}



	public String create()
	{

		if (this.conversation.isTransient())
		{
			this.conversation.begin();
		}
		return "create?faces-redirect=true&nocid=true";
	}

	public void retrieve()
	{

		if (FacesContext.getCurrentInstance().isPostback())
		{
			return;
		}

		if (this.conversation.isTransient())
		{
			this.conversation.begin();
		}

		if (this.id == null)
		{
			this.accountYear = this.example;
		}
		else
		{
			this.accountYear = ConverterDTO.accountYear(accountYearFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting AccountYear entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				accountYear.setAccountYearStatus(true);
				accountYear.setCreateDateTime(new Date());
				accountYear.setCreateBy(session.getUserProfile().getUserLoginName());
				this.accountYearFacade.persist(this.accountYear);
				return "search?faces-redirect=true";
			}
			else
			{
				accountYear.setUpdateDateTime(new Date());
				accountYear.setUpdateBy(session.getUserProfile().getUserLoginName());
				this.accountYearFacade.merge(this.accountYear);
				return "view?faces-redirect=true&id=" + this.accountYear.getId();
			}
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public String delete()
	{
		this.conversation.end();

		try
		{
			accountYear.setAccountYearStatus(false);
			this.accountYearFacade.merge(accountYear);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public void paginate()
	{
		if(example.getDateTo()!=null){
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(example.getDateTo());
			calendar.set(Calendar.MONTH, 11);
			calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
			calendar.set(Calendar.HOUR_OF_DAY, 23);
			calendar.set(Calendar.MINUTE, 59);
			calendar.set(Calendar.SECOND, 59);
			calendar.set(Calendar.MILLISECOND, 999);
			example.setDateTo(calendar.getTime());
		}
		this.count = accountYearFacade.getCount(example);
		this.pageItems = accountYearFacade.getAccountYearList(example, page, getPageSize());
	}

	public List<AccountYear> getAll()
	{
		return this.accountYearFacade.getAll();
	}

	public List<AccountYear> getByCompany()
	{
		return accountYearFacade.findByCompany(session.getCompany().getId());

	}


	public int getPage()
	{
		return this.page;
	}

	public void setPage(int page)
	{
		this.page = page;
	}

	public int getPageSize()
	{
		return 10;
	}

	public DTOAccountYear getExample()
	{
		return this.example;
	}

	public void setExample(DTOAccountYear example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public List<AccountYear> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}


	public Converter getConverter()
	{

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return accountYearFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((AccountYear) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTOAccountYear add = new DTOAccountYear();

	public DTOAccountYear getAdd()
	{
		return this.add;
	}

	public DTOAccountYear getAdded()
	{
		DTOAccountYear added = this.add;
		this.add = new DTOAccountYear();
		return added;
	}

	public DTOAccountYear getAccountYear()
	{
		return this.accountYear;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}




}