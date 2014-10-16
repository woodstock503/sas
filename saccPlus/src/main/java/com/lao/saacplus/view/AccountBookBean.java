package com.lao.saacplus.view;

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

import com.lao.saacplus.dto.DTOAccountBook;
import com.lao.saacplus.entity.AccountBook;
import com.lao.saacplus.session.AccountBookFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;

/**
 * Backing bean for AccountBook entities.
 * <p>
 * This class provides CRUD functionality for all AccountBook entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class AccountBookBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving AccountBook entities
	 */

	@Inject
	private SessionBean session;

	@EJB
	private AccountBookFacade accountBookFacade;

	private Long id;

	private DTOAccountBook accountBook;

	@Inject
	private Conversation conversation;


	private int page;
	private long count;
	private List<DTOAccountBook> pageItems;
	private DTOAccountBook example = new DTOAccountBook();
	private DTOAccountBook add = new DTOAccountBook();


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

		try{

			if (this.id == null)
			{
				accountBook = example;
			}
			else
			{
				accountBook = ConverterDTO.accountBook(accountBookFacade.findById(getId()));
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}


	/*
	 * Support updating and deleting AccountBook entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				accountBook.setAccountBookStatus(true);
				accountBook.setCreateDateTime(new Date());
				accountBook.setCreateBy(session.getUserProfile().getUserLoginName());
				this.accountBookFacade.persist(accountBook);
				return "search?faces-redirect=true";
			}
			else
			{
				accountBook.setUpdateDateTime(new Date());
				accountBook.setUpdateBy(session.getUserProfile().getUserLoginName());
				this.accountBookFacade.merge(accountBook);
				return "view?faces-redirect=true&id=" + this.accountBook.getId();
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
			accountBook.setAccountBookStatus(false);
			this.accountBookFacade.merge(accountBook);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching AccountBook entities with pagination
	 */

	public void paginate()
	{
		this.count = this.accountBookFacade.getCount(example);
		this.pageItems = accountBookFacade.getAccountBookList(example, page, getPageSize());
	}

	/*
	 * Support listing and POSTing back AccountBook entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<AccountBook> getAll()
	{
		return accountBookFacade.getAll();
	}

	public DTOAccountBook getAccountBook()
	{
		return this.accountBook;
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

	public DTOAccountBook getExample()
	{
		return this.example;
	}

	public void setExample(DTOAccountBook example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public List<DTOAccountBook> getPageItems()
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

				return accountBookFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((AccountBook) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	public DTOAccountBook getAdd()
	{
		return this.add;
	}

	public DTOAccountBook getAdded()
	{
		DTOAccountBook added = this.add;
		this.add = new DTOAccountBook();
		return added;
	}
}