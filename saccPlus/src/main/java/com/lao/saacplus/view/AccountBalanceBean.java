package com.lao.saacplus.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.entity.AccountBalance;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.session.AccountBalanceFacade;
import com.lao.saacplus.session.ChartOfAccountFacade;
import com.lao.saacplus.session.CompanyFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;

/**
 * Backing bean for AccountBalance entities.
 * <p>
 * This class provides CRUD functionality for all AccountBalance entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class AccountBalanceBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving AccountBalance entities
	 */

	@Inject
	private SessionBean session;
	@Inject
	private Conversation conversation;

	@EJB
	private CompanyFacade companyFacade;
	@EJB
	private AccountBalanceFacade accountBalanceFacade;
	@EJB
	private ChartOfAccountFacade chartOfAccountFacade;



	private int page;
	private long count;
	private List<DTOChartOfAccount> chartOfAccounts;
	private List<AccountBalance> pageItems;
	private DTOAccountBalance example = new DTOAccountBalance();
	private List<DTOAccountBalance> accountBalanceList;
	private Long id;
	private DTOAccountBalance accountBalance;


	public void create()
	{

		if (this.conversation.isTransient())
		{
			this.conversation.begin();
		}

		try{
			List<ChartOfAccount> chartOfAccountList = chartOfAccountFacade.findRootChartOfAccount(session.getCompany().getId());
			
			chartOfAccounts = new ArrayList<DTOChartOfAccount>();
			accountBalanceList = new ArrayList<DTOAccountBalance>();
			for(ChartOfAccount c:chartOfAccountList){
				chartOfAccounts.add(ConverterDTO.chartOfAccount(c));
				DTOAccountBalance accountBalance = new DTOAccountBalance();
				accountBalance.setChartOfAccount(c);
				accountBalance.setCompany(c.getCompany());
				accountBalance.setCreditBalance(new BigDecimal("0"));
				accountBalance.setDebitBalance(new BigDecimal("0"));
				accountBalanceList.add(accountBalance);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
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
			this.accountBalance = this.example;
		}
		else
		{
			this.accountBalance = ConverterDTO.accountBalance(accountBalanceFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting AccountBalance entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				this.accountBalanceFacade.persist(this.accountBalance);
				return "search?faces-redirect=true";
			}
			else
			{
				this.accountBalanceFacade.merge(this.accountBalance);
				return "view?faces-redirect=true&id=" + this.accountBalance.getId();
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
			this.accountBalanceFacade.remove(accountBalance);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public List<AccountBalance> getAll()
	{

		return this.accountBalanceFacade.getAll();
	}


	public void paginate()
	{
		this.count = accountBalanceFacade.getCount(example);
		this.pageItems = accountBalanceFacade.getAccountBalanceList(example, page, getPageSize());
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

	public DTOAccountBalance getExample()
	{
		return this.example;
	}

	public void setExample(DTOAccountBalance example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}


	public List<AccountBalance> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public List<DTOChartOfAccount> getChartOfAccounts() {
		return chartOfAccounts;
	}

	public void setChartOfAccounts(List<DTOChartOfAccount> chartOfAccounts) {
		this.chartOfAccounts = chartOfAccounts;
	}

	public DTOAccountBalance getAccountBalance()
	{
		return this.accountBalance;
	}


	public Converter getConverter()
	{

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return accountBalanceFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((AccountBalance) value).getId());
			}
		};
	}

	private DTOAccountBalance add = new DTOAccountBalance();

	public DTOAccountBalance getAdd()
	{
		return this.add;
	}

	public DTOAccountBalance getAdded()
	{
		DTOAccountBalance added = this.add;
		this.add = new DTOAccountBalance();
		return added;
	}

	public List<DTOAccountBalance> getAccountBalanceList() {
		return accountBalanceList;
	}

	public void setAccountBalanceList(List<DTOAccountBalance> accountBalanceList) {
		this.accountBalanceList = accountBalanceList;
	}


}