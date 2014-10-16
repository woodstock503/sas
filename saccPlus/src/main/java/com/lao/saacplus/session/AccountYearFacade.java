package com.lao.saacplus.session;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import com.lao.saacplus.dto.DTOAccountBalance;
import com.lao.saacplus.dto.DTOAccountYear;
import com.lao.saacplus.entity.AccountBalance;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.UserProfile;
import com.lao.saacplus.util.ConverterDTO;

@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class AccountYearFacade {


	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Resource
	private UserTransaction userTransaction;


	public AccountYear findAccountYear(Date billDate,Company company,UserProfile userProfile) throws Exception{
		AccountYear accountYear = null;
		try{
			accountYear = (AccountYear) entityManager.createQuery("From AccountYear " +
					" where accountYearStatus = :status " +
					" and startDate <= :startDate " +
					" and endDate >= :endDate " +
					" and company.id = :companyId")
					.setParameter("status", true)
					.setParameter("startDate", billDate)
					.setParameter("endDate", billDate)
					.setParameter("companyId", company.getId())
					.getSingleResult();

		}catch (NoResultException e) {

			SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
			accountYear = new AccountYear();
			accountYear.setAccountYearStatus(true);
			accountYear.setCloseStatus(false);
			accountYear.setCompany(entityManager.find(Company.class,company.getId()));
			accountYear.setCreateBy(userProfile.getUserLoginName());
			accountYear.setCreateDateTime(new Date());

			Calendar selectedDate = Calendar.getInstance();
			selectedDate.setTimeInMillis(billDate.getTime());

			Calendar startDate = Calendar.getInstance();
			startDate.setTimeInMillis(company.getStartDate().getTime());
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);

			int companyStart = Integer.parseInt(sdf.format(company.getStartDate()));
			int selectStart = Integer.parseInt(sdf.format(selectedDate.getTime()));
			if(selectStart<companyStart){
				startDate.set(Calendar.YEAR, selectedDate.get(Calendar.YEAR)-1);
				
			}else{
				startDate.set(Calendar.YEAR, selectedDate.get(Calendar.YEAR));
			}
			
			Calendar endDate = Calendar.getInstance();
			endDate.setTimeInMillis(startDate.getTimeInMillis());
			endDate.add(Calendar.YEAR, 1);
			endDate.add(Calendar.DAY_OF_YEAR, -1);
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			

			accountYear.setStartDate(startDate.getTime());
			accountYear.setEndDate(endDate.getTime());
			entityManager.persist(accountYear);
		}catch (Exception e) {
			throw e;
		}
		return  accountYear;
	}


	public AccountYear findPreviousAccountYear(Date startDate,Long companyId) throws Exception{
		AccountYear accountYear = null;
		try{

			Calendar prevAccountYearDate = Calendar.getInstance();
			prevAccountYearDate.setTime(startDate);
			prevAccountYearDate.add(Calendar.DAY_OF_MONTH, -1);
			prevAccountYearDate.set(Calendar.HOUR_OF_DAY, 0);
			prevAccountYearDate.set(Calendar.MINUTE, 0);
			prevAccountYearDate.set(Calendar.SECOND, 0);
			prevAccountYearDate.set(Calendar.MILLISECOND, 0);

			accountYear = (AccountYear) entityManager.createQuery("From AccountYear " +
					" where accountYearStatus = :status " +
					" and startDate <= :startDate " +
					" and endDate >= :endDate " +
					" and company.id = :companyId")
					.setParameter("status", true)
					.setParameter("startDate", prevAccountYearDate.getTime())
					.setParameter("endDate", prevAccountYearDate.getTime())
					.setParameter("companyId", companyId)
					.getSingleResult();

		}catch (NoResultException e) {

		}catch (Exception e) {
			throw e;
		}
		return accountYear;
	}

	public void closeAccountYear(DTOAccountYear dtoAccountYear,List<DTOAccountBalance> accountBalances) throws Exception{
		userTransaction.begin();
		try{
			merge(dtoAccountYear);
			for(DTOAccountBalance a:accountBalances){
				AccountBalance accountBalance = new AccountBalance();
				ConverterDTO.accountBalance(a,accountBalance);
				entityManager.persist(accountBalance);
			}
		}catch(Exception ex){
			userTransaction.rollback();
			ex.printStackTrace();
		}finally{
			userTransaction.commit();
		}
	}

	public List<AccountYear> findByCompany(Long companyId){
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<AccountYear> criteria = builder.createQuery(AccountYear.class);
		Root<AccountYear> root = criteria.from(AccountYear.class);

		List<Predicate> predicatesList = new ArrayList<Predicate>();
		if (companyId != null && companyId.longValue()>0)
		{
			predicatesList.add(builder.equal(root.get("company"), companyId));
		}
		Predicate[] predicates = predicatesList.toArray(new Predicate[predicatesList.size()]);
		TypedQuery<AccountYear> query = this.entityManager.createQuery(criteria.select(root).where(predicates).orderBy(builder.desc(root.<Date> get("startDate"))));
		List<AccountYear> accountYearList = query.getResultList();
		return accountYearList;
	}

	public AccountYear findById(Long id)
	{
		return this.entityManager.find(AccountYear.class, id);
	}


	public void persist(DTOAccountYear dtoAccountYear){
		AccountYear accountYear = new AccountYear();
		ConverterDTO.accountYear(dtoAccountYear, accountYear);
		this.entityManager.persist(accountYear);
	}

	public void merge(DTOAccountYear dtoAccountYear){
		AccountYear accountYear = entityManager.find(AccountYear.class, dtoAccountYear.getId());
		ConverterDTO.accountYear(dtoAccountYear, accountYear);
		this.entityManager.merge(accountYear);
	}

	@SuppressWarnings("unchecked")
	public List<AccountYear> findAccountYearByCompanyId(Long companyId){
		List<AccountYear> accountYearList = entityManager.createQuery("From AccountYear where accountYearStatus=:status and company.id=:companyId")
				.setParameter("status", true)
				.setParameter("companyId", companyId).getResultList();
		return accountYearList;
	}



	public long getCount(DTOAccountYear accountYear){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<AccountYear> root = countCriteria.from(AccountYear.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,accountYear));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<AccountYear> getAccountYearList(DTOAccountYear accountYear,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<AccountYear> criteria = builder.createQuery(AccountYear.class);
		Root<AccountYear> root = criteria.from(AccountYear.class);

		TypedQuery<AccountYear> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,accountYear)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);
		return query.getResultList();
	}


	public List<AccountYear> getAll()
	{

		CriteriaQuery<AccountYear> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(AccountYear.class);
		List<AccountYear> accountYearsList = entityManager.createQuery(
				criteria.select(criteria.from(AccountYear.class))).getResultList();


		return accountYearsList;
	}

	private Predicate[] getSearchPredicates(Root<AccountYear> root,DTOAccountYear dtoAccountYear)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		Company company = dtoAccountYear.getCompany();
		if (company != null)
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}

		if (dtoAccountYear.getDateFrom() != null) {
			predicatesList.add(builder.greaterThanOrEqualTo(root.<Date> get("startDate"), dtoAccountYear.getDateFrom()));
		}

		if (dtoAccountYear.getDateTo() != null) {
			predicatesList.add(builder.lessThanOrEqualTo(root.<Date> get("startDate"), dtoAccountYear.getDateTo()));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("accountYearStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}
}
