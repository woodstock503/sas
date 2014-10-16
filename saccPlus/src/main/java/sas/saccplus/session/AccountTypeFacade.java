package sas.saccplus.session;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sas.saccplus.dto.DTOAccountType;
import sas.saccplus.model.AccountType;
import sas.saccplus.util.ConverterDTO;


@Stateful
public class AccountTypeFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	public AccountType findById(Long id)
	{
		return this.entityManager.find(AccountType.class, id);
	}


	public void persist(DTOAccountType dtoAccountType){
		AccountType accountType = new AccountType();
		ConverterDTO.accountType(dtoAccountType, accountType);
		this.entityManager.persist(accountType);
	}

	public void merge(DTOAccountType dtoAccountType){
		AccountType accountType = entityManager.find(AccountType.class, dtoAccountType.getId());
		ConverterDTO.accountType(dtoAccountType, accountType);
		this.entityManager.merge(accountType);
	}

	public long getCount(DTOAccountType accountType){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<AccountType> root = countCriteria.from(AccountType.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,accountType));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<AccountType> getAccountTypeList(DTOAccountType accountType,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<AccountType> criteria = builder.createQuery(AccountType.class);
		Root<AccountType> root = criteria.from(AccountType.class);

		TypedQuery<AccountType> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,accountType)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<AccountType> getAll()
	{

		CriteriaQuery<AccountType> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(AccountType.class);
		List<AccountType> accountTypesList = entityManager.createQuery(
				criteria.select(criteria.from(AccountType.class))).getResultList();
		
		return accountTypesList;
	}


	private Predicate[] getSearchPredicates(Root<AccountType> root,DTOAccountType accountType)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String accountTypeName = accountType.getAccountTypeName();
		if (accountTypeName != null && !"".equals(accountTypeName))
		{
			predicatesList.add(builder.like(root.<String> get("accountTypeName"), '%' + accountTypeName + '%'));
		}
		String accountTypeNameEn = accountType.getAccountTypeNameEn();
		if (accountTypeNameEn != null && !"".equals(accountTypeNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("accountTypeNameEn"), '%' + accountTypeNameEn + '%'));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("accountTypeStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
