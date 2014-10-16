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

import sas.saccplus.dto.DTORole;
import sas.saccplus.model.Role;
import sas.saccplus.util.ConverterDTO;


@Stateful
public class RoleFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	public Role findById(Long id)
	{
		return this.entityManager.find(Role.class, id);
	}


	public void persist(DTORole dtoRole){
		Role role = new Role();
		ConverterDTO.role(dtoRole, role);
		this.entityManager.persist(role);
	}

	public void merge(DTORole dtoRole){
		Role role = entityManager.find(Role.class, dtoRole.getId());
		ConverterDTO.role(dtoRole, role);
		this.entityManager.merge(role);
	}


	public long getCount(DTORole role){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Role> root = countCriteria.from(Role.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,role));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Role> getRoleList(DTORole role,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Role> criteria = builder.createQuery(Role.class);
		Root<Role> root = criteria.from(Role.class);

		TypedQuery<Role> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,role)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);
		return query.getResultList();
	}


	public List<Role> getAll()
	{

		CriteriaQuery<Role> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Role.class);
		List<Role> rolesList = entityManager.createQuery(
				criteria.select(criteria.from(Role.class))).getResultList();

		return rolesList;
	}

	private Predicate[] getSearchPredicates(Root<Role> root,DTORole dtoRole)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String roleName = dtoRole.getRoleName();
		if (roleName != null && !"".equals(roleName))
		{
			predicatesList.add(builder.like(root.<String> get("roleName"), '%' + roleName + '%'));
		}
		String roleNameEn = dtoRole.getRoleNameEn();
		if (roleNameEn != null && !"".equals(roleNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("roleNameEn"), '%' + roleNameEn + '%'));
		}
		String createBy = dtoRole.getCreateBy();
		if (createBy != null && !"".equals(createBy))
		{
			predicatesList.add(builder.like(root.<String> get("createBy"), '%' + createBy + '%'));
		}
		String updateBy = dtoRole.getUpdateBy();
		if (updateBy != null && !"".equals(updateBy))
		{
			predicatesList.add(builder.like(root.<String> get("updateBy"), '%' + updateBy + '%'));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("roleStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
