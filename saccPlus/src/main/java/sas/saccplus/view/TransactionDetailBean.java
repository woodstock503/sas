package sas.saccplus.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sas.saccplus.model.AccountType;
import sas.saccplus.model.ChartOfAccount;
import sas.saccplus.model.Company;
import sas.saccplus.model.TransactionDetail;
import sas.saccplus.util.SessionBean;

/**
 * Backing bean for TransactionDetail entities.
 * <p>
 * This class provides CRUD functionality for all TransactionDetail entities. It focuses
 * purely on Java EE 6 standards (e.g. <tt>&#64;ConversationScoped</tt> for
 * state management, <tt>PersistenceContext</tt> for persistence,
 * <tt>CriteriaBuilder</tt> for searches) rather than introducing a CRUD framework or
 * custom base class.
 */

@Named
@Stateful
@ConversationScoped
public class TransactionDetailBean implements Serializable
{

	private static final long serialVersionUID = 1L;

	/*
	 * Support creating and retrieving TransactionDetail entities
	 */

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

	private TransactionDetail transactionDetail;

	public TransactionDetail getTransactionDetail()
	{
		return this.transactionDetail;
	}

	@Inject
	private Conversation conversation;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

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
			this.transactionDetail = this.example;
		}
		else
		{
			this.transactionDetail = findById(getId());
		}
	}

	public TransactionDetail findById(Long id)
	{

		return this.entityManager.find(TransactionDetail.class, id);
	}

	/*
	 * Support updating and deleting TransactionDetail entities
	 */

	public String update()
	{
		this.conversation.end();

		try
		{
			if (this.id == null)
			{
				this.entityManager.persist(this.transactionDetail);
				return "search?faces-redirect=true";
			}
			else
			{
				this.entityManager.merge(this.transactionDetail);
				return "view?faces-redirect=true&id=" + this.transactionDetail.getId();
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
			this.entityManager.remove(findById(getId()));
			this.entityManager.flush();
			return "search?faces-redirect=true";
		}
		catch (Exception e)
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(e.getMessage()));
			return null;
		}
	}

	/*
	 * Support searching TransactionDetail entities with pagination
	 */

	private int page;
	private long count;
	private List<TransactionDetail> pageItems;

	private TransactionDetail example = new TransactionDetail();

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

	public TransactionDetail getExample()
	{
		return this.example;
	}

	public void setExample(TransactionDetail example)
	{
		this.example = example;
	}

	public void search()
	{
		this.page = 0;
	}

	public void paginate()
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		// Populate this.count

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<TransactionDetail> root = countCriteria.from(TransactionDetail.class);
		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root));
		this.count = this.entityManager.createQuery(countCriteria)
				.getSingleResult();

		// Populate this.pageItems

		CriteriaQuery<TransactionDetail> criteria = builder.createQuery(TransactionDetail.class);
		root = criteria.from(TransactionDetail.class);
		TypedQuery<TransactionDetail> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root)));
		query.setFirstResult(this.page * getPageSize()).setMaxResults(
				getPageSize());
		this.pageItems = query.getResultList();
	}

	private Predicate[] getSearchPredicates(Root<TransactionDetail> root)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		ChartOfAccount chartOfAccount = this.example.getChartOfAccount();
		if (chartOfAccount != null)
		{
			predicatesList.add(builder.equal(root.get("chartOfAccount"), chartOfAccount));
		}
		AccountType accountType = this.example.getAccountType();
		if (accountType != null)
		{
			predicatesList.add(builder.equal(root.get("accountType"), accountType));
		}
		Company company = session.getCompany();
		if (company != null )
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}
		predicatesList.add(builder.isTrue(root.<Boolean> get("transactionDetailStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

	public List<TransactionDetail> getPageItems()
	{
		return this.pageItems;
	}

	public long getCount()
	{
		return this.count;
	}

	/*
	 * Support listing and POSTing back TransactionDetail entities (e.g. from inside an
	 * HtmlSelectOneMenu)
	 */

	public List<TransactionDetail> getAll()
	{

		CriteriaQuery<TransactionDetail> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(TransactionDetail.class);
		return this.entityManager.createQuery(
				criteria.select(criteria.from(TransactionDetail.class))).getResultList();
	}

	@Resource
	private SessionContext sessionContext;

	public Converter getConverter()
	{

		final TransactionDetailBean ejbProxy = this.sessionContext.getBusinessObject(TransactionDetailBean.class);

		return new Converter()
		{

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value)
			{

				return ejbProxy.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value)
			{

				if (value == null)
				{
					return "";
				}

				return String.valueOf(((TransactionDetail) value).getId());
			}
		};
	}

	/*
	 * Support adding children to bidirectional, one-to-many tables
	 */

	private TransactionDetail add = new TransactionDetail();

	public TransactionDetail getAdd()
	{
		return this.add;
	}

	public TransactionDetail getAdded()
	{
		TransactionDetail added = this.add;
		this.add = new TransactionDetail();
		return added;
	}
}