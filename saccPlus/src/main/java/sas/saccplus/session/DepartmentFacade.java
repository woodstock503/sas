package sas.saccplus.session;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import sas.saccplus.dto.DTODepartment;
import sas.saccplus.model.Company;
import sas.saccplus.model.Department;
import sas.saccplus.util.ConverterDTO;
import sas.saccplus.util.SessionBean;


@Stateful
public class DepartmentFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	@EJB
	private CurrencyFacade currencyFacade;

	@EJB
	private CompanyFacade companyFacade;
	
	@Inject
	private SessionBean session;

	
	public Department findById(Long id)
	{
		return this.entityManager.find(Department.class, id);
	}


	public void persist(DTODepartment dtoDepartment){
		Department department = new Department();
		ConverterDTO.department(dtoDepartment, department);
		this.entityManager.persist(department);
	}

	public void merge(DTODepartment dtoDepartment){
		Department department = entityManager.find(Department.class, dtoDepartment.getId());
		ConverterDTO.department(dtoDepartment, department);
		this.entityManager.merge(department);
	}

	@SuppressWarnings("unchecked")
	public List<Department> findDepartmentByCompanyId(Long companyId){
		List<Department> departmentList = entityManager.createQuery("From Department where departmentStatus=:status and company.id=:companyId")
				.setParameter("status", true)
				.setParameter("companyId", companyId).getResultList();

		return departmentList;
	}



	public long getCount(DTODepartment department){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Department> root = countCriteria.from(Department.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,department));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Department> getDepartmentList(DTODepartment department,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Department> criteria = builder.createQuery(Department.class);
		Root<Department> root = criteria.from(Department.class);

		TypedQuery<Department> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,department)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Department> getAll()
	{
		CriteriaQuery<Department> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Department.class);
		List<Department> departmentsList = entityManager.createQuery(
				criteria.select(criteria.from(Department.class))).getResultList();

		return departmentsList;
	}

	private Predicate[] getSearchPredicates(Root<Department> root,DTODepartment dtoDepartment)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String departmentName = dtoDepartment.getDepartmentName();
		if (departmentName != null && !"".equals(departmentName))
		{
			predicatesList.add(builder.like(root.<String> get("departmentName"), '%' + departmentName + '%'));
		}
		String departmentNameEn = dtoDepartment.getDepartmentNameEn();
		if (departmentNameEn != null && !"".equals(departmentNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("departmentNameEn"), '%' + departmentNameEn + '%'));
		}
		Company company = session.getCompany();
		if (company != null )
		{
			predicatesList.add(builder.equal(root.get("company"), company));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("departmentStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
