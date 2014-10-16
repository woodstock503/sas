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

import sas.saccplus.dto.DTOUserProfile;
import sas.saccplus.model.UserBranch;
import sas.saccplus.model.UserCompany;
import sas.saccplus.model.UserRole;
import sas.saccplus.model.Users;
import sas.saccplus.session.RoleFacade;
import sas.saccplus.session.UserProfileFacade;
import sas.saccplus.util.BeanUtil;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for UserProfile entities.
 * <p>
 * This class provides CRUD functionality for all UserProfile entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@ConversationScoped
public class UserProfileBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving UserProfile entities
	 */

	@Inject
	private BeanUtil beanUtil;
	@Inject
	private SessionBean session;
	@Inject
	private Conversation conversation;
	
	
	@EJB
	private UserProfileFacade userProfileFacade;
	@EJB
	private RoleFacade roleFacade;
	
	
	
	private Long id;
	private int page;
	private long count;
	private List<Users> pageItems;
	private DTOUserProfile example = new DTOUserProfile();
	private DTOUserProfile userProfile;

	

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
			this.userProfile = this.example;
		}
		else
		{
			this.userProfile = ConverterDTO.userProfile(userProfileFacade.findById(getId()));
			for(UserRole ur:userProfile.getUserRoles()){
				userProfile.getRoles().add(ur.getRole());
			}
			for(UserCompany cr:userProfile.getUserCompanys()){
				userProfile.getCompanys().add(cr.getCompany());
			}
			for(UserBranch ub:userProfile.getUserBranchs()){
				userProfile.getBranchs().add(ub.getBranch());
			}
		}
	}

	/*
	 * Support updating and deleting UserProfile entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				userProfile.setUserStatus(true);
				userProfile.setCreateDateTime(new Date());
				userProfile.setCreateBy(session.getUser().getUserName());
				userProfile.setUserPassword(beanUtil.encodeMD5(userProfile.getUserPassword()));
				this.userProfileFacade.persist(this.userProfile);
				return "search?faces-redirect=true";
			}
			else
			{
				userProfile.setUpdateDateTime(new Date());
				userProfile.setUpdateBy(session.getUser().getUserName());
				this.userProfileFacade.merge(this.userProfile);
				return "view?faces-redirect=true&id=" + this.userProfile.getId();
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
			userProfile.setUserStatus(false);
			this.userProfileFacade.merge(userProfile);
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

		this.count = userProfileFacade.getCount(example);
		this.pageItems = userProfileFacade.getUserProfileList(example, page, getPageSize());
	}


	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
	public DTOUserProfile getUserProfile()
	{
		return this.userProfile;
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

	public DTOUserProfile getExample()
	{
		return this.example;
	}

	public void setExample(DTOUserProfile example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}


	public List<Users> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back UserProfile entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Users> getAll()
	{
		return this.userProfileFacade.getAll();
	}


	public Converter getConverter()
	{

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return userProfileFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Users) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTOUserProfile add = new DTOUserProfile();

	public DTOUserProfile getAdd()
	{
		return this.add;
	}

	public DTOUserProfile getAdded()
	{
		DTOUserProfile added = this.add;
		this.add = new DTOUserProfile();
		return added;
	}

}