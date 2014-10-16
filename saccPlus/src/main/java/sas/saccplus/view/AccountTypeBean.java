package sas.saccplus.view;

import java.io.Serializable;
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

import sas.saccplus.dto.DTOAccountType;
import sas.saccplus.model.AccountType;
import sas.saccplus.session.AccountTypeFacade;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for AccountType entities.
 * <p>
 * This class provides CRUD functionality for all AccountType entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class AccountTypeBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving AccountType entities
	 */
	@Inject
	private SessionBean session;
	@Inject
	private Conversation conversation;
	
	
	@EJB
	private AccountTypeFacade accountTypeFacade;
	
	private Long id;
	private DTOAccountType accountType;
	private int page;
	private long count;
	private List<AccountType> pageItems;

	private DTOAccountType example = new DTOAccountType();

	
	
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
			this.accountType = this.example;
		}
		else
		{
			this.accountType = ConverterDTO.accountType(accountTypeFacade.findById(getId()));
		}
	}
	
	/*
	 * Support updating and deleting AccountType entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				accountType.setAccountTypeStatus(true);
				accountType.setCreateDateTime(new Date());
				accountType.setCreateBy(session.getUser().getUserName());
				this.accountTypeFacade.persist(this.accountType);
				return "search?faces-redirect=true";
			}
			else
			{
				accountType.setUpdateDateTime(new Date());
				accountType.setUpdateBy(session.getUser().getUserName());
				this.accountTypeFacade.merge(this.accountType);
				return "view?faces-redirect=true&id=" + this.accountType.getId();
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
			accountType.setAccountTypeStatus(false);
			this.accountTypeFacade.merge(accountType);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching AccountType entities with pagination
	 */

	public void paginate()
	{

		this.count = accountTypeFacade.getCount(example);
		this.pageItems = accountTypeFacade.getAccountTypeList(example, page, getPageSize());
	}
	
	public List<AccountType> getAll()
	{
		return accountTypeFacade.getAll();
	}

	
	public DTOAccountType getAccountType()
	{
		return this.accountType;
	}
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
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

	public DTOAccountType getExample()
	{
		return this.example;
	}

	public void setExample(DTOAccountType example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public List<AccountType> getPageItems()
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
				return accountTypeFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((AccountType) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private AccountType add = new AccountType();

	public AccountType getAdd()
	{
		return this.add;
	}

	public AccountType getAdded()
	{
		AccountType added = this.add;
		this.add = new AccountType();
		return added;
	}
}