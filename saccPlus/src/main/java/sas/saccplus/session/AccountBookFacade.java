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

import sas.saccplus.dto.DTOAccountBook;
import sas.saccplus.model.AccountBook;
import sas.saccplus.util.ConverterDTO;

/**
 * Session Bean implementation class AccountBookFacade
 */
@Stateful
public class AccountBookFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public AccountBook findById(Long id)
	{
		return this.entityManager.find(AccountBook.class, id);
	}

	public void persist(DTOAccountBook dtoAccountBook){
		AccountBook accountBook = new AccountBook();
		ConverterDTO.accountBook(dtoAccountBook, accountBook);
		this.entityManager.persist(accountBook);
	}

	public void merge(DTOAccountBook dtoAccountBook){
		AccountBook accountBook = entityManager.find(AccountBook.class, dtoAccountBook.getId());
		ConverterDTO.accountBook(dtoAccountBook, accountBook);
		this.entityManager.merge(accountBook);
	}

	@SuppressWarnings("unchecked")
	public List<AccountBook> findAccountBook(){
		List<AccountBook> accountBookList = entityManager.createQuery("From AccountBook " +
				" where accountBookStatus=:status")
				.setParameter("status", true).getResultList();
		
		return accountBookList;
	}

	public long getCount(DTOAccountBook accountBook){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<AccountBook> root = countCriteria.from(AccountBook.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,accountBook));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<DTOAccountBook> getAccountBookList(DTOAccountBook accountBook,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<AccountBook> criteria = builder.createQuery(AccountBook.class);
		Root<AccountBook> root = criteria.from(AccountBook.class);

		TypedQuery<AccountBook> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,accountBook)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);
		List<DTOAccountBook> dtoAccountBookList  = new ArrayList<DTOAccountBook>();
		for(AccountBook a:query.getResultList()){
			dtoAccountBookList.add(ConverterDTO.accountBook(a));
		}
		return dtoAccountBookList;
	}


	public List<AccountBook> getAll()
	{

		CriteriaQuery<AccountBook> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(AccountBook.class);
		List<AccountBook> accountBooksList = entityManager.createQuery(
				criteria.select(criteria.from(AccountBook.class))).getResultList();

		return accountBooksList;
	}


	private Predicate[] getSearchPredicates(Root<AccountBook> root,DTOAccountBook accountBook)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String accountBookCode = accountBook.getAccountBookCode();
		if (accountBookCode != null && !"".equals(accountBookCode))
		{
			predicatesList.add(builder.like(root.<String> get("accountBookCode"), '%' + accountBookCode + '%'));
		}
		String accountBookName = accountBook.getAccountBookName();
		if (accountBookName != null && !"".equals(accountBookName))
		{
			predicatesList.add(builder.like(root.<String> get("accountBookName"), '%' + accountBookName + '%'));
		}
		String accountBookNameEn = accountBook.getAccountBookNameEn();
		if (accountBookNameEn != null && !"".equals(accountBookNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("accountBookNameEn"), '%' + accountBookNameEn + '%'));
		}
		predicatesList.add(builder.isTrue(root.<Boolean> get("accountBookStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}
}
