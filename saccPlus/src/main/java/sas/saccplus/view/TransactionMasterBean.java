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

import sas.saccplus.dto.DTOTransactionMaster;
import sas.saccplus.model.TransactionMaster;
import sas.saccplus.session.CompanyFacade;
import sas.saccplus.session.TransactionMasterFacade;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for TransactionMaster entities.
 * <p>
 * This class provides CRUD functionality for all TransactionMaster entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class TransactionMasterBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving TransactionMaster entities
	 */

	@EJB
	private CompanyFacade companyFacade;
	
	@EJB
	private TransactionMasterFacade transactionMasterFacade;

	@Inject
	private SessionBean session;
	private Long id;

	

	private int page;
	private long count;
	private List<TransactionMaster> pageItems;
	private DTOTransactionMaster transactionMaster;
	private DTOTransactionMaster add = new DTOTransactionMaster();
	private DTOTransactionMaster example = new DTOTransactionMaster();
	
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
			this.transactionMaster = this.example;
		}
		else
		{
			this.transactionMaster = ConverterDTO.transactionMaster(transactionMasterFacade.findById(getId()));
		}
	}


	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				transactionMaster.setTransactionMasterStatus(true);
				transactionMaster.setCreateDateTime(new Date());
				transactionMaster.setCreateBy(session.getUser().getUserName());
				this.transactionMasterFacade.persist(this.transactionMaster);
				return "search?faces-redirect=true";
			}
			else
			{
				transactionMaster.setUpdateDateTime(new Date());
				transactionMaster.setUpdateBy(session.getUser().getUserName());
				this.transactionMasterFacade.merge(this.transactionMaster);
				return "view?faces-redirect=true&id=" + this.transactionMaster.getId();
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
			transactionMaster.setTransactionMasterStatus(false);
			this.transactionMasterFacade.remove(transactionMaster);
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

	public DTOTransactionMaster getExample()
	{
		return this.example;
	}

	public void setExample(DTOTransactionMaster example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{
		this.count = transactionMasterFacade.getCount(example);
		this.pageItems = transactionMasterFacade.getTransactionMasterList(example, page, getPage());
	}


	public List<TransactionMaster> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	public List<TransactionMaster> getAll()
	{
		return this.transactionMasterFacade.getAll();
	}


	public Converter getConverter()
	{

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return transactionMasterFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((DTOTransactionMaster) value).getId());
			}
		};
	}


	public DTOTransactionMaster getAdd()
	{
		return this.add;
	}

	public DTOTransactionMaster getAdded()
	{
		DTOTransactionMaster added = this.add;
		this.add = new DTOTransactionMaster();
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

	public DTOTransactionMaster getTransactionMaster()
	{
		return this.transactionMaster;
	}




}