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

import sas.saccplus.dto.DTOCurrency;
import sas.saccplus.model.Currency;
import sas.saccplus.session.CurrencyFacade;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for Currency entities.
 * <p>
 * This class provides CRUD functionality for all Currency entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class CurrencyBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Currency entities
	 */
	@EJB
	private CurrencyFacade currencyFacade;

	@Inject
	private SessionBean session;

	@Inject
	private Conversation conversation;


	private Long id;

	private DTOCurrency currency;

	private int page;
	private long count;
	private List<Currency> pageItems;

	private DTOCurrency example = new DTOCurrency();

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
			this.currency = this.example;
		}
		else
		{
			this.currency = ConverterDTO.currency(currencyFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting Currency entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				currency.setCurrencyStatus(true);
				currency.setCreateDateTime(new Date());
				currency.setCreateBy(session.getUser().getUserName());
				this.currencyFacade.persist(this.currency);
				return "search?faces-redirect=true";
			}
			else
			{
				currency.setUpdateDateTime(new Date());
				currency.setUpdateBy(session.getUser().getUserName());
				this.currencyFacade.merge(this.currency);
				return "view?faces-redirect=true&id=" + this.currency.getId();
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
			currency.setCurrencyStatus(false);
			this.currencyFacade.merge(currency);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	public DTOCurrency getCurrency()
	{
		return this.currency;
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

	public DTOCurrency getExample()
	{
		return this.example;
	}

	public void setExample(DTOCurrency example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{

		try {
			this.count = currencyFacade.getCount(example);
			this.pageItems = currencyFacade.getCurrencyList(example, page, getPageSize());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	public List<Currency> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Currency entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Currency> getAll()
	{
		return currencyFacade.getAll();
	}


	public Converter getConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return currencyFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Currency) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Currency add = new Currency();

	public Currency getAdd()
	{
		return this.add;
	}

	public Currency getAdded()
	{
		Currency added = this.add;
		this.add = new Currency();
		return added;
	}
}