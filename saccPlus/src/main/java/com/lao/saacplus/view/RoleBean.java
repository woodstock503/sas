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

import com.lao.saacplus.dto.DTORole;
import com.lao.saacplus.entity.Role;
import com.lao.saacplus.session.RoleFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;

/**
 * Backing bean for Role entities.
 * <p>
 * This class provides CRUD functionality for all Role entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class RoleBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Role entities
	 */

	@EJB
	private RoleFacade roleFacade;

	@Inject
	private SessionBean session;


	private Long id;

	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	private DTORole role;

	public DTORole getRole()
	{
		return this.role;
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
			this.role = this.example;
		}
		else
		{
			this.role = ConverterDTO.role(roleFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting Role entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				role.setRoleStatus(true);
				role.setCreateDateTime(new Date());
				role.setCreateBy(session.getUserProfile().getUserLoginName());
				this.roleFacade.persist(this.role);
				return "search?faces-redirect=true";
			}
			else
			{
				role.setUpdateDateTime(new Date());
				role.setUpdateBy(session.getUserProfile().getUserLoginName());
				this.roleFacade.merge(this.role);
				return "view?faces-redirect=true&id=" + this.role.getId();
			}
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return "create";
		}
	}

	public String delete()
	{
		this.conversation.end();

		try
		{
			role.setRoleStatus(false);
			this.roleFacade.merge(role);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching Role entities with pagination
	 */

	private int page;
	private long count;
	private List<Role> pageItems;

	private DTORole example = new DTORole();

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

	public DTORole getExample()
	{
		return this.example;
	}

	public void setExample(DTORole example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{
		this.count = roleFacade.getCount(example);
		this.pageItems = roleFacade.getRoleList(example, page, getPageSize());
	}

	public List<Role> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Role entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Role> getAll()
	{	
		return roleFacade.getAll();
	}

	public Converter getConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return roleFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Role) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTORole add = new DTORole();

	public DTORole getAdd()
	{
		return this.add;
	}

	public DTORole getAdded()
	{
		DTORole added = this.add;
		this.add = new DTORole();
		return added;
	}
}