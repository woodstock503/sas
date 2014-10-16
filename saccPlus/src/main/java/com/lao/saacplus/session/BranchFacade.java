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

import com.lao.saacplus.dto.DTOBranch;
import com.lao.saacplus.entity.Branch;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.UserBranch;
import com.lao.saacplus.entity.UserProfile;
import com.lao.saacplus.util.ConverterDTO;


@Stateful
public class BranchFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;


	public Branch findById(Long id)
	{
		return this.entityManager.find(Branch.class, id);
	}


	public void persist(DTOBranch dtoBranch){
		Branch branch = new Branch();
		ConverterDTO.branch(dtoBranch, branch);
		this.entityManager.persist(branch);
	}

	public void merge(DTOBranch dtoBranch){
		Branch branch = entityManager.find(Branch.class, dtoBranch.getId());
		ConverterDTO.branch(dtoBranch, branch);
		this.entityManager.merge(branch);
	}

	@SuppressWarnings("unchecked")
	public List<Branch> findBranchByUser(UserProfile userProfile,Long companyId){

		List<UserBranch> userBranchList = entityManager.createQuery("From UserBranch " +
				" where userProfile.id=:userProfileId")
				.setParameter("userProfileId", userProfile.getId()).getResultList();

		List<Branch> branchList = new ArrayList<Branch>();
		StringBuffer sql = new StringBuffer("From Branch " +
				" where branchStatus=:status  " +
				" and company.id=:companyId" +
				" and ( id=0 ");
		if(!userBranchList.isEmpty()){
			for(UserBranch uc:userBranchList){
				sql.append(" or id=").append(uc.getBranch().getId());
			}
			sql.append(")");
			Query query = entityManager.createQuery(sql.toString());
			query.setParameter("status", true);
			query.setParameter("companyId", companyId).getResultList();
			branchList = query.getResultList();
		}
		//		List<Branch> branchList = entityManager.createQuery("From Branch where branchStatus=:status and company.id=:companyId")
		//				.setParameter("status", true)
		//				.setParameter("companyId", userProfile.getId()).getResultList();
		return branchList;
	}



	public long getCount(DTOBranch branch){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Branch> root = countCriteria.from(Branch.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,branch));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Branch> getBranchList(DTOBranch branch,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Branch> criteria = builder.createQuery(Branch.class);
		Root<Branch> root = criteria.from(Branch.class);

		TypedQuery<Branch> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,branch)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);
		return query.getResultList();
	}


	public List<Branch> getAll()
	{

		CriteriaQuery<Branch> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Branch.class);
		List<Branch> branchsList = entityManager.createQuery(
				criteria.select(criteria.from(Branch.class))).getResultList();

		return branchsList;
	}

	private Predicate[] getSearchPredicates(Root<Branch> root,DTOBranch dtoBranch)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String branchName = dtoBranch.getBranchName();
		if (branchName != null && !"".equals(branchName))
		{
			predicatesList.add(builder.like(root.<String> get("branchName"), '%' + branchName + '%'));
		}
		String branchNameEn = dtoBranch.getBranchNameEn();
		if (branchNameEn != null && !"".equals(branchNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("branchNameEn"), '%' + branchNameEn + '%'));
		}      
		Company company = dtoBranch.getCompany();
		if (company != null)
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}
		predicatesList.add(builder.isTrue(root.<Boolean> get("branchStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}
}
