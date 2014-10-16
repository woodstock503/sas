package com.lao.saacplus.view;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import com.lao.saacplus.dto.DTODepartment;
import com.lao.saacplus.entity.Department;
import com.lao.saacplus.session.DepartmentFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;

/**
 * Backing bean for Department entities.
 * <p>
 * This class provides CRUD functionality for all Department entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class DepartmentBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Department entities
	 */
	@Inject
	private SessionBean session;
	
	
	@EJB
	private DepartmentFacade departmentFacade;
	
	private DTODepartment department;
	@Inject
	private Conversation conversation;
	
	private Long id;
	private int page;
	private long count;
	private List<Department> pageItems;

	private DTODepartment example = new DTODepartment();



	


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
			this.department = this.example;
		}
		else
		{
			this.department = ConverterDTO.department(departmentFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting Department entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				department.setDepartmentStatus(true);
				department.setCreateDateTime(new Date());
				department.setCreateBy(session.getUserProfile().getUserLoginName());
				department.setCompany(session.getCompany());
				this.departmentFacade.persist(this.department);
				return "search?faces-redirect=true";
			}
			else
			{
				department.setUpdateDateTime(new Date());
				department.setUpdateBy(session.getUserProfile().getUserLoginName());
				this.departmentFacade.merge(this.department);
				return "view?faces-redirect=true&id=" + this.department.getId();
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
			department.setDepartmentStatus(false);
			this.departmentFacade.merge(department);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	public DTODepartment getDepartment()
	{
		return this.department;
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

	public DTODepartment getExample()
	{
		return this.example;
	}

	public void setExample(DTODepartment example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{
		this.count = departmentFacade.getCount(example);
		this.pageItems = departmentFacade.getDepartmentList(example, page, getPageSize());
	}


	public List<Department> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Department entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Department> getAll()
	{
		return departmentFacade.getAll();
	}


	public Converter getConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return departmentFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Department) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTODepartment add = new DTODepartment();

	public DTODepartment getAdd()
	{
		return this.add;
	}

	public DTODepartment getAdded()
	{
		DTODepartment added = this.add;
		this.add = new DTODepartment();
		return added;
	}
}