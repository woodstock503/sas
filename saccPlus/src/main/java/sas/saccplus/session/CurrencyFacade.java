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

import sas.saccplus.dto.DTOCurrency;
import sas.saccplus.model.Currency;
import sas.saccplus.util.ConverterDTO;

/**
 * Session Bean implementation class CurrencyFacade
 */
@Stateful
public class CurrencyFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	
	public Currency findById(Long id)
	{
		return this.entityManager.find(Currency.class, id);
	}


	public void persist(DTOCurrency dtoCurrency) throws Exception{
		Currency currency = new Currency();
		ConverterDTO.currency(dtoCurrency, currency);
		this.entityManager.persist(currency);
	}

	public void merge(DTOCurrency dtoCurrency) throws Exception{
		Currency currency = entityManager.find(Currency.class, dtoCurrency.getId());
		ConverterDTO.currency(dtoCurrency, currency);
		this.entityManager.merge(currency);
	}

	public long getCount(DTOCurrency currency) throws Exception{
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Currency> root = countCriteria.from(Currency.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,currency));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Currency> getCurrencyList(DTOCurrency currency,int page,int pageSize) throws Exception
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Currency> criteria = builder.createQuery(Currency.class);
		Root<Currency> root = criteria.from(Currency.class);

		TypedQuery<Currency> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,currency)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Currency> getAll()
	{

		CriteriaQuery<Currency> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Currency.class);
		List<Currency> currencysList = entityManager.createQuery(
				criteria.select(criteria.from(Currency.class))).getResultList();


		return currencysList;
	}


	private Predicate[] getSearchPredicates(Root<Currency> root,DTOCurrency currency)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String country = currency.getCountry();
		if (country != null && !"".equals(country))
		{
			predicatesList.add(builder.like(root.<String> get("country"), '%' + country + '%'));
		}
		String countryEn = currency.getCountryEn();
		if (countryEn != null && !"".equals(countryEn))
		{
			predicatesList.add(builder.like(root.<String> get("countryEn"), '%' + countryEn + '%'));
		}
		String abbreviation = currency.getAbbreviation();
		if (abbreviation != null && !"".equals(abbreviation))
		{
			predicatesList.add(builder.like(root.<String> get("abbreviation"), '%' + abbreviation + '%'));
		}
		String createBy = currency.getCreateBy();
		if (createBy != null && !"".equals(createBy))
		{
			predicatesList.add(builder.like(root.<String> get("createBy"), '%' + createBy + '%'));
		}
		String updateBy = currency.getUpdateBy();
		if (updateBy != null && !"".equals(updateBy))
		{
			predicatesList.add(builder.like(root.<String> get("updateBy"), '%' + updateBy + '%'));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("currencyStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}
}
