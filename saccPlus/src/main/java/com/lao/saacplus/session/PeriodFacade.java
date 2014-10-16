package com.lao.saacplus.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lao.saacplus.dto.DTOPeriod;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.Period;
import com.lao.saacplus.util.ConverterDTO;


@Stateful
public class PeriodFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public Period findById(Long id)
	{
		return this.entityManager.find(Period.class, id);
	}


	public void persist(DTOPeriod dtoPeriod) throws Exception{
		Period period = new Period();
		ConverterDTO.period(dtoPeriod, period);
		this.entityManager.persist(period);
	}

	public void merge(DTOPeriod dtoPeriod) throws Exception{
		Period period = entityManager.find(Period.class, dtoPeriod.getId());
		ConverterDTO.period(dtoPeriod, period);
		this.entityManager.merge(period);
	}
	
	public void persist(Period period) throws Exception{
		this.entityManager.persist(period);
	}

	public void remove(DTOPeriod dtoPeriod) throws Exception{
		Period period = entityManager.find(Period.class, dtoPeriod.getId());
		this.entityManager.remove(period);
	}

	public Period findPeriod(Date billDate,AccountYear accountYear,Company company) throws Exception{
		Period period = null;
		try{
			period = (Period) entityManager.createQuery("From Period " +
					" where startDate <= :startDate " +
					" and endDate >= :endDate " +
					" and company.id = :companyId")
					.setParameter("startDate", billDate)
					.setParameter("endDate", billDate)
					.setParameter("companyId", company.getId())
					.getSingleResult();

		}catch (NoResultException e) {

			period = new Period();
			period.setCloseStatus(false);
			period.setCompany(entityManager.find(Company.class,company.getId()));
			period.setAccountYear(entityManager.find(AccountYear.class,accountYear.getId()));


			Calendar selectedBillDate = Calendar.getInstance();
			selectedBillDate.setTimeInMillis(billDate.getTime());

			int periodNo = company.getPeriod();
			int addNo = 12/periodNo;
			int periodCount = 0;

			for(int i = 0;i<periodNo*addNo;i+=addNo){
				periodCount++;
				Calendar startCalendar = Calendar.getInstance();
				startCalendar.setTimeInMillis(billDate.getTime());
				startCalendar.set(Calendar.MONTH, 0);
				startCalendar.set(Calendar.DAY_OF_MONTH, 1);
				startCalendar.set(Calendar.HOUR_OF_DAY, 0);
				startCalendar.set(Calendar.MINUTE, 0);
				startCalendar.set(Calendar.SECOND, 0);
				startCalendar.set(Calendar.MILLISECOND, 0);
				startCalendar.add(Calendar.MONTH, i);

				Calendar endCalendar = Calendar.getInstance();
				endCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
				endCalendar.add(Calendar.MONTH, addNo-1);
				endCalendar.set(Calendar.DAY_OF_MONTH, endCalendar.getActualMaximum(Calendar.DAY_OF_MONTH));
				startCalendar.set(Calendar.HOUR_OF_DAY, 23);
				startCalendar.set(Calendar.MINUTE, 59);
				startCalendar.set(Calendar.SECOND, 59);
				startCalendar.set(Calendar.MILLISECOND, 999);

				if(startCalendar.compareTo(selectedBillDate)<=0&&endCalendar.compareTo(selectedBillDate)>=0){
					period.setStartDate(startCalendar.getTime());
					period.setEndDate(endCalendar.getTime());
					period.setPeriodNo(periodCount);
					break;
				}

			}

			entityManager.persist(period);
		}catch (Exception e) {
			throw e;
		}
		return period;
	}

	public long getCount(DTOPeriod accountType){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Period> root = countCriteria.from(Period.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,accountType));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Period> getPeriodList(DTOPeriod period,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Period> criteria = builder.createQuery(Period.class);
		Root<Period> root = criteria.from(Period.class);

		TypedQuery<Period> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,period)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Period> getAll()
	{

		CriteriaQuery<Period> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Period.class);
		List<Period> accountTypesList = entityManager.createQuery(
				criteria.select(criteria.from(Period.class))).getResultList();

		return accountTypesList;
	}


	private Predicate[] getSearchPredicates(Root<Period> root,DTOPeriod accountType)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		Company company = accountType.getCompany();
		if (company != null)
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}

		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
