package sas.saccplus.view;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import sas.saccplus.dto.DTOCompany;
import sas.saccplus.model.Company;
import sas.saccplus.session.CompanyFacade;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for Company entities.
 * <p>
 * This class provides CRUD functionality for all Company entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@SessionScoped
public class CompanyBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving Company entities
	 */

	@Inject
	private SessionBean session;


	private Long id;
	private int page;
	private long count;
	private List<Company> pageItems;

	private DTOCompany example = new DTOCompany();
	@EJB
	private CompanyFacade companyFacade;

	
	private DTOCompany company;


	public String create()
	{

		company = new DTOCompany();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.set(Calendar.MONTH, 0);
		startCalendar.set(Calendar.DAY_OF_MONTH, 1);
		startCalendar.set(Calendar.HOUR_OF_DAY, 0);
		startCalendar.set(Calendar.MINUTE, 0);
		startCalendar.set(Calendar.SECOND, 0);
		startCalendar.set(Calendar.MILLISECOND, 0);
		example.setStartDate(startCalendar.getTime());
		
		
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.set(Calendar.MONTH, 11);
		endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		endCalendar.set(Calendar.HOUR_OF_DAY, 23);
		endCalendar.set(Calendar.MINUTE, 59);
		endCalendar.set(Calendar.SECOND, 59);
		endCalendar.set(Calendar.MILLISECOND, 999);
		example.setEndDate(endCalendar.getTime());
		
		example.setDigitPattern("#,##0.00");
		example.setPeriod(12);
		id = null;
		return "create?faces-redirect=true";
	}

	public void retrieve()
	{

		if (FacesContext.getCurrentInstance().isPostback())
		{
			return;
		}

		if (this.id == null)
		{
			this.company = this.example;
		}
		else
		{
			this.company = ConverterDTO.company(companyFacade.findById(getId()));
		}
	}

	/*
	 * Support updating and deleting Company entities
	 */

	public String update()
	{
		try
		{

			if (this.id == null)
			{
				company.setCompanyStatus(true);
				company.setCreateDateTime(new Date());
				company.setCreateBy(session.getUser().getUserName());

				this.companyFacade.persist(this.company);
				company = null;
				example = new DTOCompany();
				return "search?faces-redirect=true";
			}
			else
			{
				company.setUpdateDateTime(new Date());
				company.setUpdateBy(session.getUser().getUserName());
				this.companyFacade.merge(this.company);
				example = new DTOCompany();
				return "view?faces-redirect=true&id=" + this.company.getId();
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
		try
		{
			company.setCompanyStatus(false);
			this.companyFacade.merge(company);
			company = null;
			example = new DTOCompany();
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
		this.count = companyFacade.getCount(example);
		this.pageItems = companyFacade.getCompanyList(example, page, getPageSize());
	}
	
	public Long getId()
	{
		return this.id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public DTOCompany getCompany()
	{
		return this.company;
	}
	
	public Company getCompanyAdd()
	{
		Company c = new Company();
		ConverterDTO.company(company, c);
		return c;
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

	public DTOCompany getExample()
	{
		return this.example;
	}

	public void setExample(DTOCompany example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public List<Company> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back Company entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<Company> getAll()
	{
		return companyFacade.getAll();
	}

	public Converter getConverter()
	{

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return companyFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((Company) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private DTOCompany add = new DTOCompany();

	public DTOCompany getAdd()
	{
		return this.add;
	}

	public DTOCompany getAdded()
	{
		DTOCompany added = this.add;
		this.add = new DTOCompany();
		return added;
	}



	public void paint(OutputStream stream, Object object) throws IOException {
		if(getCompany().getLogo()!=null){
			stream.write(getCompany().getLogo());
			stream.close();
		}
	}

	public void listener(FileUploadEvent event) throws Exception {
		UploadedFile item = event.getUploadedFile();
		company.setLogo(item.getData());

	}

	public String clearUploadData() {	
		return null;
	}

	public long getTimeStamp() {
		return System.currentTimeMillis();
	}


}