package com.lao.saacplus.session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

import com.lao.saacplus.dto.DTOTransactionDetail;
import com.lao.saacplus.dto.DTOTransactionMaster;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.Period;
import com.lao.saacplus.entity.TransactionDetail;
import com.lao.saacplus.entity.TransactionMaster;
import com.lao.saacplus.util.ConverterDTO;

@Stateful
@TransactionManagement(TransactionManagementType.BEAN)
public class TransactionMasterFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@Resource
	private UserTransaction userTransaction;


	public TransactionMaster findById(Long id)
	{
		return this.entityManager.find(TransactionMaster.class, id);
	}

	public void persist(DTOTransactionMaster dtoTransactionMaster){
		TransactionMaster transactionMaster = new TransactionMaster();
		ConverterDTO.transactionMaster(dtoTransactionMaster, transactionMaster);
		this.entityManager.persist(transactionMaster);
	}

	public void merge(DTOTransactionMaster dtoTransactionMaster){
		TransactionMaster transactionMaster = entityManager.find(TransactionMaster.class, dtoTransactionMaster.getId());
		ConverterDTO.transactionMaster(dtoTransactionMaster, transactionMaster);
		this.entityManager.merge(transactionMaster);
	}

	public void persist(TransactionMaster transactionMaster){
		this.entityManager.persist(transactionMaster);
	}

	public void merge(TransactionMaster transactionMaster){
		this.entityManager.merge(transactionMaster);
	}

	public void remove(DTOTransactionMaster dtoTransactionMaster){
		this.entityManager.remove(entityManager.find(TransactionMaster.class, dtoTransactionMaster.getId()));
	}

	
	@EJB
	private AccountYearFacade accountYearFacade;
	@EJB
	private PeriodFacade periodFacade;
	public void saveNewJournalTransactionMaster(DTOTransactionMaster dtoTransactionMaster,List<DTOTransactionDetail> transactionList) throws Exception{

		userTransaction.begin();
		try{
			
			AccountYear accountYear = accountYearFacade.findAccountYear(dtoTransactionMaster.getBillDate(),dtoTransactionMaster.getCompany(),dtoTransactionMaster.getCreateUser());
			if(accountYear.getCloseStatus()){
				throw new Exception("AccountYear is Colosed.");
			}
			Period period = periodFacade.findPeriod(dtoTransactionMaster.getBillDate(),accountYear,dtoTransactionMaster.getCompany());
			if(period.getCloseStatus()){
				throw new Exception("Period is Colosed.");
			}
			
			
			TransactionMaster transactionMaster = new TransactionMaster();
			ConverterDTO.transactionMaster(dtoTransactionMaster, transactionMaster);
			transactionMaster.setAccountYear(accountYear);
			transactionMaster.setPeriod(period);
			transactionMaster.setPeriodNo(period.getPeriodNo());
			int row = 0;
			for(DTOTransactionDetail t:transactionList){
				if(t.getChartOfAccount()!=null){
					row++;
					t.setAccountBook(transactionMaster.getAccountBook());
					t.setAccountYear(transactionMaster.getAccountYear());
					t.setBranch(transactionMaster.getBranch());
					t.setCompany(transactionMaster.getCompany());
					t.setCreateBy(transactionMaster.getCreateBy());
					t.setCreateDateTime(new Date());
					t.setTransactionDetailStatus(true);
					t.setPeriod(transactionMaster.getPeriod());
					t.setPeriodNo(transactionMaster.getPeriodNo());
					t.setOrderNo(row);
					t.setTransactionMaster(transactionMaster);
					
					TransactionDetail detail = new TransactionDetail();
					ConverterDTO.transactionDetail(t,detail);
					transactionMaster.getTransactionDetails().add(detail);
				}
			}
			this.entityManager.persist(transactionMaster);
		}catch(Exception ex){
			userTransaction.rollback();
			throw ex;
		}finally{
			userTransaction.commit();
		}
	}

	public void updateNewJournalTransactionMaster(DTOTransactionMaster dtoTransactionMaster,List<DTOTransactionDetail> transactionList) throws Exception{

		userTransaction.begin();
		try{
			
			AccountYear accountYear = accountYearFacade.findAccountYear(dtoTransactionMaster.getBillDate(),dtoTransactionMaster.getCompany(),dtoTransactionMaster.getCreateUser());
			if(accountYear.getCloseStatus()){
				throw new Exception("AccountYear is Colosed.");
			}
			Period period = periodFacade.findPeriod(dtoTransactionMaster.getBillDate(),accountYear,dtoTransactionMaster.getCompany());
			if(period.getCloseStatus()){
				throw new Exception("Period is Colosed.");
			}

			TransactionMaster transactionMaster = entityManager.find(TransactionMaster.class, dtoTransactionMaster.getId());
			ConverterDTO.transactionMaster(dtoTransactionMaster, transactionMaster);
			transactionMaster.setAccountYear(accountYear);
			transactionMaster.setPeriod(period);
			transactionMaster.setPeriodNo(period.getPeriodNo());
			transactionMaster.getTransactionDetails().clear();
			
			int row = 0;
			for(DTOTransactionDetail t:transactionList){
				if(t.getChartOfAccount()!=null){
					row++;
					t.setOrderNo(row);
					t.setUpdateBy(transactionMaster.getUpdateBy());
					t.setUpdateDateTime(new Date());
					t.setAccountBook(transactionMaster.getAccountBook());
					t.setAccountYear(transactionMaster.getAccountYear());
					t.setPeriod(transactionMaster.getPeriod());
					t.setPeriodNo(transactionMaster.getPeriodNo());
					if(t.getId()==null){
						t.setBranch(transactionMaster.getBranch());
						t.setCompany(transactionMaster.getCompany());
						t.setCreateBy(transactionMaster.getCreateBy());
						t.setCreateDateTime(new Date());
						t.setTransactionDetailStatus(true);
					}else{
						t.setUpdateBy(transactionMaster.getUpdateBy());
						t.setUpdateDateTime(new Date());
					}
					t.setId(null);
					t.setTransactionMaster(transactionMaster);
					
					TransactionDetail detail = new TransactionDetail();
					ConverterDTO.transactionDetail(t,detail);
					transactionMaster.getTransactionDetails().add(detail);
				}
			}
			entityManager.merge(transactionMaster);
		}catch(Exception ex){
			userTransaction.rollback();
			throw ex;
		}finally{
			userTransaction.commit();
		}
	}
	
	@SuppressWarnings("unchecked")
	public int maxCertifyTransactionMasterOnDay(){
		List<TransactionMaster> dataList = new ArrayList<TransactionMaster>();
		int certifyCount = 0;
		try{
			Calendar startDate = Calendar.getInstance();
			startDate.set(Calendar.HOUR_OF_DAY, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
			startDate.set(Calendar.MILLISECOND, 0);
			
			Calendar endDate = Calendar.getInstance();
			endDate.set(Calendar.HOUR_OF_DAY, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 59);
			endDate.set(Calendar.MILLISECOND, 999);
			
			dataList = entityManager.createQuery("From TransactionMaster " +
					" where createDateTime " +
					" between :startDate and :endDate" +
					" order by createDateTime desc")
					.setParameter("startDate", startDate.getTime())
					.setParameter("endDate", endDate.getTime())
					.getResultList();
			if(!dataList.isEmpty()){
				String m = dataList.get(0).getCertifyNo().split("-")[1];
				certifyCount = Integer.valueOf(m);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return certifyCount;
	}

	public long getCount(DTOTransactionMaster transactionMaster){

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<TransactionMaster> root = countCriteria.from(TransactionMaster.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,transactionMaster));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<TransactionMaster> getTransactionMasterList(DTOTransactionMaster transactionMaster,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<TransactionMaster> criteria = builder.createQuery(TransactionMaster.class);
		Root<TransactionMaster> root = criteria.from(TransactionMaster.class);

		TypedQuery<TransactionMaster> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,transactionMaster))
				.orderBy(builder.desc(root.<Date>get("updateDateTime")))
				.orderBy(builder.desc(root.<Date>get("createDateTime"))));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<TransactionMaster> getAll()
	{

		CriteriaQuery<TransactionMaster> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(TransactionMaster.class);
		List<TransactionMaster> transactionMastersList = entityManager.createQuery(
				criteria.select(criteria.from(TransactionMaster.class))).getResultList();

		return transactionMastersList;
	}


	private Predicate[] getSearchPredicates(Root<TransactionMaster> root,DTOTransactionMaster transactionMaster)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		if (transactionMaster.getDateFrom() != null) {
			predicatesList.add(builder.greaterThanOrEqualTo(root.<Date> get("billDate"), transactionMaster.getDateFrom()));
		}

		if (transactionMaster.getDateTo() != null) {
			predicatesList.add(builder.lessThanOrEqualTo(root.<Date> get("billDate"), transactionMaster.getDateTo()));
		}

		String certifyNo = transactionMaster.getCertifyNo();
		if (certifyNo != null && !"".equals(certifyNo))
		{
			predicatesList.add(builder.like(root.<String> get("certifyNo"), '%' + certifyNo + '%'));
		}

		String referenceNo = transactionMaster.getReferenceNo();
		if (referenceNo != null && !"".equals(referenceNo))
		{
			predicatesList.add(builder.like(root.<String> get("referenceNo"), '%' + referenceNo + '%'));
		}

		Company company = transactionMaster.getCompany();
		if (company != null)
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("transactionMasterStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
