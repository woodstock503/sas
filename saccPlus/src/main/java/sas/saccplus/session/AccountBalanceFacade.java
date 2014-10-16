package sas.saccplus.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sas.saccplus.dto.DTOAccountBalance;
import sas.saccplus.model.AccountBalance;
import sas.saccplus.model.AccountYear;
import sas.saccplus.model.ChartOfAccount;
import sas.saccplus.model.Company;
import sas.saccplus.model.Users;
import sas.saccplus.util.ConverterDTO;


@Stateful
public class AccountBalanceFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;
	
	@EJB
	private AccountYearFacade accountYearFacade;

	public AccountBalance findById(Long id)
	{
		return this.entityManager.find(AccountBalance.class, id);
	}

	public void persist(DTOAccountBalance dtoAccountBalance){
		AccountBalance accountBalance = new AccountBalance();
		ConverterDTO.accountBalance(dtoAccountBalance, accountBalance);
		this.entityManager.persist(accountBalance);
	}

	public void merge(DTOAccountBalance dtoAccountBalance){
		AccountBalance accountBalance = entityManager.find(AccountBalance.class, dtoAccountBalance.getId());
		ConverterDTO.accountBalance(dtoAccountBalance, accountBalance);
		this.entityManager.merge(accountBalance);
	}
	
	public void persist(AccountBalance accountBalance){
		this.entityManager.persist(accountBalance);
	}

	public void merge(AccountBalance accountBalance){
		this.entityManager.merge(accountBalance);
	}

	public void remove(DTOAccountBalance dtoAccountBalance){
		AccountBalance accountBalance = entityManager.find(AccountBalance.class, dtoAccountBalance.getId());
		this.entityManager.remove(accountBalance);
	}

	@SuppressWarnings("unchecked")
	public List<AccountBalance> findAccountBalanceByCompanyId(Long companyId){
		List<AccountBalance> accountBalanceList = entityManager.createQuery("From AccountBalance where accountBalanceStatus=:status and company.id=:companyId")
				.setParameter("status", true)
				.setParameter("companyId", companyId).getResultList();
		return accountBalanceList;
	}


	@SuppressWarnings("unchecked")
	public List<AccountBalance> findBeginingBalance(Long companyId){
		List<AccountBalance> accountBalanceList = entityManager.createQuery("From AccountBalance " +
				" where company.id=:companyId " +
				" and isBeginBalance=:isBeginBalance")
				.setParameter("companyId", companyId)
				.setParameter("isBeginBalance", true).getResultList();
		return accountBalanceList;
	}
	
	public void saveOrUpdateChartOfAccount(List<AccountBalance> accountBalanceList,Date balanceDate,Company company,Users userProfile) throws Exception{
		AccountYear accountYear = accountYearFacade.findAccountYear(balanceDate, company, userProfile);
		for(AccountBalance acc:accountBalanceList){
			acc.setOpeningAccountYear(accountYear);
			if(acc.getId()==null){
				entityManager.persist(acc);
			}else{
				entityManager.merge(acc);
			}
		}
	}



	public long getCount(DTOAccountBalance accountBalance){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<AccountBalance> root = countCriteria.from(AccountBalance.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,accountBalance));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<AccountBalance> getAccountBalanceList(DTOAccountBalance accountBalance,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<AccountBalance> criteria = builder.createQuery(AccountBalance.class);
		Root<AccountBalance> root = criteria.from(AccountBalance.class);

		TypedQuery<AccountBalance> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,accountBalance)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);
		return query.getResultList();
	}


	public List<AccountBalance> getAll()
	{

		CriteriaQuery<AccountBalance> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(AccountBalance.class);
		List<AccountBalance> accountBalancesList = entityManager.createQuery(
				criteria.select(criteria.from(AccountBalance.class))).getResultList();

		return accountBalancesList;
	}

	private Predicate[] getSearchPredicates(Root<AccountBalance> root,DTOAccountBalance dtoAccountBalance)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		ChartOfAccount chartOfAccount = dtoAccountBalance.getChartOfAccount();
		if (chartOfAccount != null)
		{
			predicatesList.add(builder.equal(root.get("chartOfAccount"), chartOfAccount));
		}
		Company company = dtoAccountBalance.getCompany();
		if (company != null)
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}
		AccountYear balanceAccountYear = dtoAccountBalance.getBalanceAccountYear();
		if (balanceAccountYear != null)
		{
			predicatesList.add(builder.equal(root.get("balanceAccountYear"), balanceAccountYear));
		}
		AccountYear openingAccountYear = dtoAccountBalance.getOpeningAccountYear();
		if (openingAccountYear != null)
		{
			predicatesList.add(builder.equal(root.get("openingAccountYear"), openingAccountYear));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
