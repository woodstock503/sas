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

import sas.saccplus.dto.DTORate;
import sas.saccplus.model.Rate;
import sas.saccplus.session.RateFacade;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for Rate entities.
 * <p>
 * This class provides CRUD functionality for all Rate entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class RateBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Rate entities
	 */
	@EJB
	private RateFacade rateFacade;
	
	
	@Inject
	private SessionBean session;
	private Long id;	
	private Date rateDateFrom;
	private Date rateDateTo;
	
	private DTORate rate;
	private int page;
	private long count;
	private List<Rate> pageItems;

	private DTORate example = new DTORate();
	private DTORate add = new DTORate();
	

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	

	public DTORate getRate()
	{
		return this.rate;
	}

	@Inject
	private Conversation conversation;



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
			this.rate = this.example;
		}
		else
		{
			this.rate = ConverterDTO.rate(rateFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting Rate entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				rate.setRateStatus(true);
				rate.setCreateDateTime(new Date());
				rate.setCreateBy(session.getUser().getUserName());
				this.rateFacade.persist(this.rate);
				return "search?faces-redirect=true";
			}
			else
			{
				rate.setUpdateDateTime(new Date());
				rate.setUpdateBy(session.getUser().getUserName());
				this.rateFacade.merge(this.rate);
				return "view?faces-redirect=true&id=" + this.rate.getId();
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
			rate.setRateStatus(false);
			this.rateFacade.merge(rate);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
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

	public DTORate getExample()
	{
		return this.example;
	}

	public void setExample(DTORate example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{

		this.count = rateFacade.getCount(example);
		this.pageItems = rateFacade.getRateList(example, page, getPageSize());
	}


	public List<Rate> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Rate entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Rate> getAll()
	{
		return rateFacade.getAll();
	}

	public Converter getConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return rateFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Rate) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	public DTORate getAdd()
	{
		return this.add;
	}

	public DTORate getAdded()
	{
		DTORate added = this.add;
		this.add = new DTORate();
		return added;
	}

	public SessionBean getSession() {
		return session;
	}

	public void setSession(SessionBean session) {
		this.session = session;
	}

	public Date getRateDateFrom() {
		return rateDateFrom;
	}

	public void setRateDateFrom(Date rateDateFrom) {
		this.rateDateFrom = rateDateFrom;
	}

	public Date getRateDateTo() {
		return rateDateTo;
	}

	public void setRateDateTo(Date rateDateTo) {
		this.rateDateTo = rateDateTo;
	}


}