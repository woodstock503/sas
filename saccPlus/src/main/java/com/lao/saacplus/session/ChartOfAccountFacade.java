package com.lao.saacplus.session;

import java.util.List;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaQuery;

import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.util.ConverterDTO;

@Stateful
public class ChartOfAccountFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	
	public ChartOfAccount findById(Long id)
	{
		return this.entityManager.find(ChartOfAccount.class, id);
	}

	public void persist(DTOChartOfAccount dtoChartOfAccount) throws Exception{
		ChartOfAccount chartOfAccount = new ChartOfAccount();
		ConverterDTO.chartOfAccount(dtoChartOfAccount,chartOfAccount);
		this.entityManager.persist(chartOfAccount);
	}

	public void merge(DTOChartOfAccount dtoChartOfAccount) throws Exception{
		ChartOfAccount chartOfAccount = entityManager.find(ChartOfAccount.class, dtoChartOfAccount.getId());
		ConverterDTO.chartOfAccount(dtoChartOfAccount,chartOfAccount);
		this.entityManager.merge(chartOfAccount);
	}
	
	public void remove(DTOChartOfAccount dtoChartOfAccount) throws Exception{
		entityManager.remove(entityManager.find(ChartOfAccount.class, dtoChartOfAccount.getId()));
	}

	@SuppressWarnings("unchecked")
	public List<ChartOfAccount> findRootChartOfAccount(Long companyId) throws Exception{
		List<ChartOfAccount> chartOfAccountList = entityManager.createQuery("From ChartOfAccount " +
				" where (chartOfAccountCodeMain is null or chartOfAccountCodeMain ='') " +
				" and chartOfAccountStatus=:status" +
				" and company.id=:companyId " +
				" order by chartOfAccountCode ")
				.setParameter("status", true)
				.setParameter("companyId",companyId).getResultList();
		 
		
		return chartOfAccountList;
	}

	@SuppressWarnings("unchecked")
	public List<ChartOfAccount> findInactiveChartOfAccount(Long companyId) throws Exception{
		List<ChartOfAccount> chartOfAccountList = entityManager.createQuery("From ChartOfAccount " +
				" where chartOfAccountStatus=:status " +
				" and company.id=:companyId ")
				.setParameter("status", false)
				.setParameter("companyId",companyId).getResultList();
		return chartOfAccountList;
	}


	@SuppressWarnings("unchecked")
	public List<ChartOfAccount> findUnderChartOfAccount(String code,Long companyId) throws Exception{
		List<ChartOfAccount> chartOfAccountList  = entityManager.createQuery("From ChartOfAccount " +
				" where chartOfAccountCodeMain=:codeMain " +
				" and chartOfAccountStatus=:status" +
				" and company.id=:companyId")
				.setParameter("codeMain", code)
				.setParameter("status", true)
				.setParameter("companyId", companyId)
				.getResultList();
		return chartOfAccountList;
	}
	
	public List<ChartOfAccount> getAll()
	{

		CriteriaQuery<ChartOfAccount> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(ChartOfAccount.class);
		List<ChartOfAccount> currencysList = entityManager.createQuery(
				criteria.select(criteria.from(ChartOfAccount.class))).getResultList();

		return currencysList;
	}
}
