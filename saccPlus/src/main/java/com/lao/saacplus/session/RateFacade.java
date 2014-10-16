package com.lao.saacplus.session;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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

import com.lao.saacplus.dto.DTORate;
import com.lao.saacplus.entity.Currency;
import com.lao.saacplus.entity.Rate;
import com.lao.saacplus.util.ConverterDTO;



@Stateful
public class RateFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@SuppressWarnings("unchecked")
	public List<Rate> findRate(){
		List<Object[]> objList = (List<Object[]>) entityManager.createNativeQuery("select distinct currency_id,id " +
				" from rate r " +
				" where rateStatus = :status " +
				" and createDateTime = (select max(createDateTime) " +
				" from rate where currency_id = r.currency_id)")
				.setParameter("status", true)
				.getResultList();
		List<Rate> dtoRateList = new ArrayList<Rate>();
		for(Object[] o:objList){
			Rate rate = entityManager.find(Rate.class, ((BigInteger)o[1]).longValue());
			dtoRateList.add(rate);
		}
		return dtoRateList;
	}

	public Rate findCurrentRateByCurrency(Long currencyId) throws Exception{
		try{
			Object[] obj = (Object[]) entityManager.createNativeQuery("select distinct currency_id,id " +
					" from rate r " +
					" where rateStatus = :status " +
					" and currency_id=:currencyId" +
					" and createDateTime = (select max(createDateTime) " +
					" from rate where currency_id = r.currency_id)")
					.setParameter("status", true)
					.setParameter("currencyId", currencyId).getSingleResult();
			return entityManager.find(Rate.class, ((BigInteger)obj[1]).longValue());
		}catch(Exception e){
			throw new Exception("not found rate");
		}
	}
	
	public Rate findById(Long id)
	{
		return this.entityManager.find(Rate.class, id);
	}


	public void persist(DTORate dtoRate){
		Rate rate = new Rate();
		ConverterDTO.rate(dtoRate, rate);
		this.entityManager.persist(rate);
	}

	public void merge(DTORate dtoRate){
		Rate rate = entityManager.find(Rate.class, dtoRate.getId());
		ConverterDTO.rate(dtoRate, rate);
		this.entityManager.merge(rate);
	}



	public long getCount(DTORate rate){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Rate> root = countCriteria.from(Rate.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,rate));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Rate> getRateList(DTORate rate,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Rate> criteria = builder.createQuery(Rate.class);
		Root<Rate> root = criteria.from(Rate.class);

		TypedQuery<Rate> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,rate)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Rate> getAll()
	{

		CriteriaQuery<Rate> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Rate.class);
		List<Rate> accountTypesList = entityManager.createQuery(
				criteria.select(criteria.from(Rate.class))).getResultList();

		return accountTypesList;
	}

	private Predicate[] getSearchPredicates(Root<Rate> root,DTORate dtoRate)
	{
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		if (dtoRate.getDateFrom() != null) {
			predicatesList.add(builder.greaterThanOrEqualTo(root.<Date> get("rateDate"), dtoRate.getDateFrom()));
		}

		if (dtoRate.getDateTo() != null) {
			predicatesList.add(builder.lessThanOrEqualTo(root.<Date> get("rateDate"), dtoRate.getDateTo()));
		}

		Currency currency = dtoRate.getCurrency();
		if (currency != null )
		{
			predicatesList.add(builder.equal(root.get("currency"), currency));
		}
		

		predicatesList.add(builder.isTrue(root.<Boolean> get("rateStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
