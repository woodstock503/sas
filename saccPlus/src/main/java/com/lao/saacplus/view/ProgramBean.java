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

import com.lao.saacplus.dto.DTOProgram;
import com.lao.saacplus.entity.Program;
import com.lao.saacplus.entity.ProgramRole;
import com.lao.saacplus.session.ProgramFacade;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;

/**
 * Backing bean for Program entities.
 * <p>
 * This class provides CRUD functionality for all Program entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class ProgramBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Program entities
	 */

	@Inject
	private SessionBean session;

	@Inject
	private Conversation conversation;

	@EJB
	private ProgramFacade programFacade;

	private Long id;


	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	private DTOProgram program;

	public DTOProgram getProgram()
	{
		return this.program;
	}



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
			this.program = this.example;
		}
		else
		{
			this.program = ConverterDTO.program(programFacade.findById(getId()));
			for(ProgramRole pr:program.getProgramRoles()){
				program.getRoles().add(pr.getRole());
			}
		}
	}

	/*
	 * Support updating and deleting Program entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				program.setProgramStatus(true);
				program.setCreateDateTime(new Date());
				program.setCreateBy(session.getUserProfile().getUserLoginName());
				this.programFacade.persist(this.program);
				return "search?faces-redirect=true";
			}
			else
			{
				program.setUpdateDateTime(new Date());
				program.setUpdateBy(session.getUserProfile().getUserLoginName());
				this.programFacade.merge(this.program);
				return "view?faces-redirect=true&id=" + this.program.getId();
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
			program.setProgramStatus(false);
			programFacade.merge(program);
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching Program entities with pagination
	 */

	private int page;
	private long count;
	private List<Program> pageItems;

	private DTOProgram example = new DTOProgram();

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

	public DTOProgram getExample()
	{
		return this.example;
	}

	public void setExample(DTOProgram example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{
		this.count = programFacade.getCount(example);
		this.pageItems = programFacade.getProgramList(example, page, getPageSize());
	}


	public List<Program> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Program entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Program> getAll()
	{
		return programFacade.getAll();
	}

	public Converter getConverter()
	{
		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return programFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((DTOProgram) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTOProgram add = new DTOProgram();

	public DTOProgram getAdd()
	{
		return this.add;
	}

	public DTOProgram getAdded()
	{
		DTOProgram added = this.add;
		this.add = new DTOProgram();
		return added;
	}
}