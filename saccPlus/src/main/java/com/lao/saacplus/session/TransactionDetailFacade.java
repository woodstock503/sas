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

import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.dto.DTOTransactionDetail;
import com.lao.saacplus.entity.TransactionDetail;
import com.lao.saacplus.util.ConverterDTO;


@Stateful
public class TransactionDetailFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;


	public TransactionDetail findById(Long id)
	{
		return this.entityManager.find(TransactionDetail.class, id);
	}


	public void persist(DTOTransactionDetail dtoTransactionDetail){
		TransactionDetail transactionDetail = new TransactionDetail();
		ConverterDTO.transactionDetail(dtoTransactionDetail, transactionDetail);
		this.entityManager.persist(transactionDetail);
	}

	public void merge(DTOTransactionDetail dtoTransactionDetail){
		TransactionDetail accountType = entityManager.find(TransactionDetail.class, dtoTransactionDetail.getId());
		ConverterDTO.transactionDetail(dtoTransactionDetail, accountType);
		this.entityManager.merge(accountType);
	}

	public void remove(TransactionDetail transactionDetail)
	{
		this.entityManager.remove(transactionDetail);
	}

	@SuppressWarnings("unchecked")
	public List<TransactionDetail> findTransactionDetail(List<DTOChartOfAccount> siblingChartList,Long accountYearId) throws Exception{
		StringBuilder q = new StringBuilder("From TransactionDetail");
		q.append(" where accountYear.id = :accountYearId ");
		q.append(" and transactionDetailStatus = :transactionDetailStatus");

		boolean first = true;
		for(DTOChartOfAccount ac:siblingChartList){
			if(first){
				q.append(" and (chartOfAccount.id = ").append(ac.getId());
				first = false;
			}else{
				q.append(" or chartOfAccount.id = ").append(ac.getId());
			}
		}
		q.append(")");
		Query query = entityManager.createQuery(q.toString())
				.setParameter("accountYearId", accountYearId)
				.setParameter("transactionDetailStatus", true);

		return query.getResultList();
	}


	public long getCount(DTOTransactionDetail transactionDetail){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<TransactionDetail> root = countCriteria.from(TransactionDetail.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,transactionDetail));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<TransactionDetail> getTransactionDetailList(DTOTransactionDetail transactionDetail,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<TransactionDetail> criteria = builder.createQuery(TransactionDetail.class);
		Root<TransactionDetail> root = criteria.from(TransactionDetail.class);

		TypedQuery<TransactionDetail> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,transactionDetail)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	@SuppressWarnings("unchecked")
	public List<TransactionDetail> findTransactionDetailByTransactionMaster(Long transactionMasterId)
	{
		List<TransactionDetail> transactionDetailList = entityManager.createQuery("From TransactionDetail " +
				" where transactionMaster.id=:transactionMasterId" +
				" and transactionDetailStatus=:transactionDetailStatus order by orderNo")
				.setParameter("transactionMasterId", transactionMasterId)
				.setParameter("transactionDetailStatus", true)
				.getResultList();

		return transactionDetailList;
	}

	public List<TransactionDetail> getAll()
	{

		CriteriaQuery<TransactionDetail> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(TransactionDetail.class);
		List<TransactionDetail> accountTypesList = entityManager.createQuery(
				criteria.select(criteria.from(TransactionDetail.class))).getResultList();

		return accountTypesList;
	}

	private Predicate[] getSearchPredicates(Root<TransactionDetail> root,DTOTransactionDetail dtoTransactionDetail)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();
		predicatesList.add(builder.isTrue(root.<Boolean> get("transactionDetailStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
