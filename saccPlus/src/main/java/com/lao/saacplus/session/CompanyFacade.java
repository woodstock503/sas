package com.lao.saacplus.session;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.lao.saacplus.dto.DTOCompany;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.Period;
import com.lao.saacplus.entity.UserCompany;
import com.lao.saacplus.entity.UserProfile;
import com.lao.saacplus.util.ConverterDTO;


@Stateful
public class CompanyFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;



	public Company findById(Long id)
	{
		return this.entityManager.find(Company.class, id);
	}

	public void persist(DTOCompany dtoCompany) throws Exception{
		Company company = new Company();
		ConverterDTO.company(dtoCompany, company);
		this.entityManager.persist(company);
		
		for(Period period:dtoCompany.getPeriods()){
			period.setCompany(company);
			this.entityManager.persist(period);
		}
	}

	public void merge(DTOCompany dtoCompany) throws Exception{
		Company company = entityManager.find(Company.class, dtoCompany.getId());
		ConverterDTO.company(dtoCompany, company);
		company.getPeriods().clear();
		this.entityManager.merge(company);
	
		for(Period period:dtoCompany.getPeriods()){
			period.setCompany(company);
			this.entityManager.persist(period);
		}
	}


	@SuppressWarnings("unchecked")
	public List<Company> findCompanyListByUser(UserProfile userProfile){
		List<UserCompany> userCompanyList = entityManager.createQuery("From UserCompany " +
				" where userProfile.id=:userProfileId")
				.setParameter("userProfileId", userProfile.getId()).getResultList();
		
		List<Company> companyList = new ArrayList<Company>();
		StringBuffer sql = new StringBuffer("From Company " +
				" where companyStatus=:status " +
				" and (id=0 ");
		
		if(!userCompanyList.isEmpty()){
			for(UserCompany uc:userCompanyList){
				sql.append(" or id=").append(uc.getCompany().getId());
			}
			sql.append(")");
			
			Query query = entityManager.createQuery(sql.toString());
			query.setParameter("status", true).getResultList();
			companyList = query.getResultList();
		}
		return companyList;
	}
	
	@SuppressWarnings("unchecked")
	public List<Company> findCompany(){
		 List<Company> companyList = entityManager.createQuery("From Company " +
				" where companyStatus=:status ")
				.setParameter("status", true).getResultList();
		return companyList;
	}

	public long getCount(DTOCompany accountType){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Company> root = countCriteria.from(Company.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,accountType));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Company> getCompanyList(DTOCompany accountType,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Company> criteria = builder.createQuery(Company.class);
		Root<Company> root = criteria.from(Company.class);

		TypedQuery<Company> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,accountType)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Company> getAll()
	{

		CriteriaQuery<Company> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Company.class);
		List<Company> accountTypesList = entityManager.createQuery(
				criteria.select(criteria.from(Company.class))).getResultList();


		return accountTypesList;
	}


	private Predicate[] getSearchPredicates(Root<Company> root,DTOCompany dtoCompany)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String companyName = dtoCompany.getCompanyName();
		if (companyName != null && !"".equals(companyName))
		{
			predicatesList.add(builder.like(root.<String> get("companyName"), '%' + companyName + '%'));
		}
		String companyNameEn = dtoCompany.getCompanyNameEn();
		if (companyNameEn != null && !"".equals(companyNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("companyNameEn"), '%' + companyNameEn + '%'));
		}
		String taxID = dtoCompany.getTaxId();
		if (taxID != null && !"".equals(taxID))
		{
			predicatesList.add(builder.like(root.<String> get("taxId"), '%' + taxID + '%'));
		}
		predicatesList.add(builder.isTrue(root.<Boolean> get("companyStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
