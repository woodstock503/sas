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

import sas.saccplus.dto.DTOUserProfile;
import sas.saccplus.model.Branch;
import sas.saccplus.model.Company;
import sas.saccplus.model.Role;
import sas.saccplus.model.UserBranch;
import sas.saccplus.model.UserCompany;
import sas.saccplus.model.UserRole;
import sas.saccplus.model.Users;
import sas.saccplus.util.ConverterDTO;


@Stateful
public class UserProfileFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;


	public Users findById(Long id)
	{
		return this.entityManager.find(Users.class, id);
	}


	public void persist(DTOUserProfile dtoUserProfile){
		Users userProfile = new Users();
		ConverterDTO.userProfile(dtoUserProfile, userProfile);
		for(Role role:dtoUserProfile.getRoles()){
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(userProfile);
			userProfile.getUserRoles().add(userRole);
		}
		for(Company company:dtoUserProfile.getCompanys()){
			UserCompany userCompany = new UserCompany();
			userCompany.setUser(userProfile);
			userCompany.setCompany(company);
			userProfile.getUserCompanys().add(userCompany);
		}
		for(Branch branch:dtoUserProfile.getBranchs()){
			UserBranch userBranch = new UserBranch();
			userBranch.setUser(userProfile);
			userBranch.setBranch(branch);
			userProfile.getUserBranchs().add(userBranch);
		}
		this.entityManager.persist(userProfile);
	}

	public void merge(DTOUserProfile dtoUserProfile){
		Users userProfile = entityManager.find(Users.class, dtoUserProfile.getId());
		ConverterDTO.userProfile(dtoUserProfile, userProfile);
		userProfile.getUserRoles().clear();
		userProfile.getUserCompanys().clear();
		userProfile.getUserBranchs().clear();
		for(Role role:dtoUserProfile.getRoles()){
			UserRole userRole = new UserRole();
			userRole.setRole(role);
			userRole.setUser(userProfile);
			userProfile.getUserRoles().add(userRole);
		}
		for(Company company:dtoUserProfile.getCompanys()){
			UserCompany userCompany = new UserCompany();
			userCompany.setUser(userProfile);
			userCompany.setCompany(company);
			userProfile.getUserCompanys().add(userCompany);
		}
		for(Branch branch:dtoUserProfile.getBranchs()){
			UserBranch userBranch = new UserBranch();
			userBranch.setUser(userProfile);
			userBranch.setBranch(branch);
			userProfile.getUserBranchs().add(userBranch);
		}
		this.entityManager.merge(userProfile);
	}


	@SuppressWarnings("unchecked")
	public Users findByUsername(String username) throws Exception
	{
		try{
			List<Users> userProfile = entityManager.createQuery("From UserProfile " +
					" where userStatus=:userStatus " +
					" and userLoginName=:username ")
					.setParameter("userStatus", true)
					.setParameter("username", username).getResultList();
			if(userProfile.isEmpty()){
				return null;
			}
			return userProfile.get(0);
		}catch(Exception ex){
			throw ex;
		}
	}




	public long getCount(DTOUserProfile userProfile){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Users> root = countCriteria.from(Users.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,userProfile));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Users> getUserProfileList(DTOUserProfile userProfile,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Users> criteria = builder.createQuery(Users.class);
		Root<Users> root = criteria.from(Users.class);

		TypedQuery<Users> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,userProfile)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Users> getAll()
	{

		CriteriaQuery<Users> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Users.class);
		List<Users> userProfilesList = entityManager.createQuery(
				criteria.select(criteria.from(Users.class))).getResultList();

		return userProfilesList;
	}

	private Predicate[] getSearchPredicates(Root<Users> root,DTOUserProfile dtoUserProfile)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String userLoginName = dtoUserProfile.getUserLoginName();
		if (userLoginName != null && !"".equals(userLoginName))
		{
			predicatesList.add(builder.like(root.<String> get("userLoginName"), '%' + userLoginName + '%'));
		}
		String userFullName = dtoUserProfile.getUserFullName();
		if (userFullName != null && !"".equals(userFullName))
		{
			predicatesList.add(builder.like(root.<String> get("userFullName"), '%' + userFullName + '%'));
		}
		String userFullNameEn = dtoUserProfile.getUserFullNameEn();
		if (userFullNameEn != null && !"".equals(userFullNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("userFullNameEn"), '%' + userFullNameEn + '%'));
		}
		String userPassword = dtoUserProfile.getUserPassword();
		if (userPassword != null && !"".equals(userPassword))
		{
			predicatesList.add(builder.like(root.<String> get("userPassword"), '%' + userPassword + '%'));
		}
		String inActiveReason = dtoUserProfile.getInActiveReason();
		if (inActiveReason != null && !"".equals(inActiveReason))
		{
			predicatesList.add(builder.like(root.<String> get("inActiveReason"), '%' + inActiveReason + '%'));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("userStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
