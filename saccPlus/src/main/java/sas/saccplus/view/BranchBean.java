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

import sas.saccplus.dto.DTOBranch;
import sas.saccplus.model.Branch;
import sas.saccplus.session.BranchFacade;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for Branch entities.
 * <p>
 * This class provides CRUD functionality for all Branch entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class BranchBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Branch entities
	 */
	@Inject
	private Conversation conversation;
	
	@Inject
	private SessionBean session;
	
	@EJB
	private BranchFacade branchFacade;
	
	private DTOBranch branch;
	
	
	private Long id;

	private int page;
	private long count;
	private List<Branch> pageItems;

	private DTOBranch example = new DTOBranch();


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
			this.branch = this.example;
		}
		else
		{
			this.branch = ConverterDTO.branch(branchFacade.findById(getId()));
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

	public DTOBranch getBranch()
	{
		return this.branch;
	}

	/*
	 * Support updating and deleting Branch entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				branch.setBranchStatus(true);
				branch.setCreateDateTime(new Date());
				branch.setCreateBy(session.getUser().getUserName());
				this.branchFacade.persist(this.branch);
				return "search?faces-redirect=true";
			}
			else
			{
				branch.setUpdateDateTime(new Date());
				branch.setUpdateBy(session.getUser().getUserName());
				this.branchFacade.merge(this.branch);
				return "view?faces-redirect=true&id=" + this.branch.getId();
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
			branch.setBranchStatus(false);
			this.branchFacade.merge(branch);
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

	public DTOBranch getExample()
	{
		return this.example;
	}

	public void setExample(DTOBranch example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{

		this.count = branchFacade.getCount(example);
		this.pageItems = branchFacade.getBranchList(example, page, getPage());
	}

	

	public List<Branch> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Branch entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Branch> getAll()
	{
		return branchFacade.getAll();
	}


	public Converter getConverter()
	{

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return branchFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Branch) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTOBranch add = new DTOBranch();

	public DTOBranch getAdd()
	{
		return this.add;
	}

	public DTOBranch getAdded()
	{
		DTOBranch added = this.add;
		this.add = new DTOBranch();
		return added;
	}

}