package com.lao.saacplus.view;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateful;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.lao.saacplus.entity.UserProfile;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


@Named
@Stateful
@RequestScoped
public class TestReportBean implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 1802764837855788586L;

	@PersistenceContext(type = PersistenceContextType.EXTENDED)
	private EntityManager entityManager;

	private JasperPrint jasperPrint;


	public void PDF() throws IOException, JRException{
		//		try {
		List<UserProfile> userProfileList = entityManager.createQuery("From UserProfile").getResultList();
		JRBeanCollectionDataSource jrBeanCollectionDataSource = new JRBeanCollectionDataSource(userProfileList);
		jasperPrint = JasperFillManager.fillReport(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/reports/jasper/test.jasper"), new HashMap(),jrBeanCollectionDataSource);

		FacesContext facesContext = FacesContext.getCurrentInstance(); //Get the context ONCE
		HttpServletResponse response = (HttpServletResponse)facesContext.getExternalContext().getResponse();

		ServletOutputStream servletOutputStream = response.getOutputStream();
		response.setContentType("application/pdf");
		//		response.addHeader("Content-Disposition", "inline; filename=report.pdf");
		facesContext.responseComplete();

		JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);

		servletOutputStream.flush();
		servletOutputStream.close();

	}
}
