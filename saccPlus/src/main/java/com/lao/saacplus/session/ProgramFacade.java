package com.lao.saacplus.session;

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

import com.lao.saacplus.dto.DTOProgram;
import com.lao.saacplus.entity.Program;
import com.lao.saacplus.entity.ProgramRole;
import com.lao.saacplus.entity.Role;
import com.lao.saacplus.util.ConverterDTO;


@Stateful
public class ProgramFacade {

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;


	public Program findById(Long id)
	{
		return this.entityManager.find(Program.class, id);
	}


	public void persist(DTOProgram dtoProgram){
		Program program = new Program();
		ConverterDTO.program(dtoProgram, program);
		for(Role role:dtoProgram.getRoles()){
			ProgramRole programRole = new ProgramRole();
			programRole.setRole(role);
			programRole.setProgram(program);
			program.getProgramRoles().add(programRole);
		}
		this.entityManager.persist(program);
	}

	public void merge(DTOProgram dtoProgram){
		Program program = entityManager.find(Program.class, dtoProgram.getId());
		ConverterDTO.program(dtoProgram, program);
		program.getProgramRoles().clear();
		
		for(Role role:dtoProgram.getRoles()){
			ProgramRole programRole = new ProgramRole();
			programRole.setRole(role);
			programRole.setProgram(program);
			program.getProgramRoles().add(programRole);
		}
		this.entityManager.merge(program);
	}

	@SuppressWarnings("unchecked")
	public List<Program> findProgramByCompanyId(Long companyId){
		List<Program> programList = entityManager.createQuery("From Program where programStatus=:status and company.id=:companyId")
				.setParameter("status", true)
				.setParameter("companyId", companyId).getResultList();

		return programList;
	}



	public long getCount(DTOProgram program){
		// Populate this.count
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Long> countCriteria = builder.createQuery(Long.class);
		Root<Program> root = countCriteria.from(Program.class);

		countCriteria = countCriteria.select(builder.count(root)).where(
				getSearchPredicates(root,program));

		return this.entityManager.createQuery(countCriteria)
				.getSingleResult();
	}


	public List<Program> getProgramList(DTOProgram program,int page,int pageSize)
	{
		// Populate this.pageItems
		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();

		CriteriaQuery<Program> criteria = builder.createQuery(Program.class);
		Root<Program> root = criteria.from(Program.class);

		TypedQuery<Program> query = this.entityManager.createQuery(criteria
				.select(root).where(getSearchPredicates(root,program)));
		query.setFirstResult(page * pageSize).setMaxResults(pageSize);

		return query.getResultList();
	}


	public List<Program> getAll()
	{

		CriteriaQuery<Program> criteria = this.entityManager
				.getCriteriaBuilder().createQuery(Program.class);
		List<Program> programsList = entityManager.createQuery(
				criteria.select(criteria.from(Program.class))).getResultList();

		return programsList;
	}

	private Predicate[] getSearchPredicates(Root<Program> root,DTOProgram dtoProgram)
	{

		CriteriaBuilder builder = this.entityManager.getCriteriaBuilder();
		List<Predicate> predicatesList = new ArrayList<Predicate>();

		String programName = dtoProgram.getProgramName();
		if (programName != null && !"".equals(programName))
		{
			predicatesList.add(builder.like(root.<String> get("programName"), '%' + programName + '%'));
		}
		String programNameEn = dtoProgram.getProgramNameEn();
		if (programNameEn != null && !"".equals(programNameEn))
		{
			predicatesList.add(builder.like(root.<String> get("programNameEn"), '%' + programNameEn + '%'));
		}
		String url = dtoProgram.getUrl();
		if (url != null && !"".equals(url))
		{
			predicatesList.add(builder.like(root.<String> get("url"), '%' + url + '%'));
		}
		String description = dtoProgram.getDescription();
		if (description != null && !"".equals(description))
		{
			predicatesList.add(builder.like(root.<String> get("description"), '%' + description + '%'));
		}
		String createBy = dtoProgram.getCreateBy();
		if (createBy != null && !"".equals(createBy))
		{
			predicatesList.add(builder.like(root.<String> get("createBy"), '%' + createBy + '%'));
		}

		predicatesList.add(builder.isTrue(root.<Boolean> get("programStatus")));
		return predicatesList.toArray(new Predicate[predicatesList.size()]);
	}

}
