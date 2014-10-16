package com.lao.saacplus.view;

import java.io.Serializable;
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

import com.lao.saacplus.dto.DTOPeriod;
import com.lao.saacplus.entity.Period;
import com.lao.saacplus.session.CompanyFacade;
import com.lao.saacplus.session.PeriodFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;

/**
 * Backing bean for Period entities.
 * <p>
 * This class provides CRUD functionality for all Period entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class PeriodBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Period entities
	 */

	@EJB
	private CompanyFacade companyFacade;

	@EJB
	private PeriodFacade periodFacade;


	private Long id;


	@Inject
	private Conversation conversation;


	@Inject
	private SessionBean session;
	private int page;
	private long count;
	private List<Period> pageItems;

	private DTOPeriod example = new DTOPeriod();
	private DTOPeriod period;


	public String create()
	{

		this.conversation.begin();
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
			this.period = this.example;
		}
		else
		{
			this.period = ConverterDTO.period(periodFacade.findById(getId()));
		}
	}
	/*
	 * Support updating and deleting Period entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				this.periodFacade.persist(this.period);
				return "search?faces-redirect=true";
			}
			else
			{
				this.periodFacade.merge(this.period);
				return "view?faces-redirect=true&id=" + this.period.getId();
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
			this.periodFacade.remove(period);
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
		this.count = periodFacade.getCount(example);
		this.pageItems = periodFacade.getPeriodList(example, page, getPageSize());
	}
	public List<Period> getAll()
	{
		return periodFacade.getAll();
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

	public DTOPeriod getExample()
	{
		return this.example;
	}

	public void setExample(DTOPeriod example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public List<Period> getPageItems()
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

				return periodFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((DTOPeriod) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private Period add = new Period();

	public Period getAdd()
	{
		return this.add;
	}

	public Period getAdded()
	{
		Period added = this.add;
		this.add = new Period();
		return added;
	}

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	

	public DTOPeriod getPeriod()
	{
		return this.period;
	}
}