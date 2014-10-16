package com.lao.saacplus.view;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.richfaces.component.UITree;
import org.richfaces.event.TreeSelectionChangeEvent;

import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.Currency;
import com.lao.saacplus.session.ChartOfAccountFacade;
import com.lao.saacplus.session.CompanyFacade;
import com.lao.saacplus.session.CurrencyFacade;
import com.lao.saacplus.util.BeanUtil;
import com.lao.saacplus.util.ConverterDTO;
import com.lao.saacplus.util.SessionBean;




@Named
@ConversationScoped
public class ChartOfAccountBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SessionBean session;

	@Inject
	private BeanUtil util;

	@Inject
	private Conversation conversation;

	@EJB
	private ChartOfAccountFacade chartOfAccountFacade;

	@EJB
	private CurrencyFacade currencyFacade;

	@EJB
	private CompanyFacade companyFacade;


	private List<DTOChartOfAccount> rootNodes;

	private List<Currency> currencyList;

	private DTOChartOfAccount model;


	private boolean showAdd;
	private boolean showView;
	private boolean showEdit;

	private String selectedNode;



	@PostConstruct
	public void initOraganizeTree(){
		try{

			currencyList = currencyFacade.getAll();
			if(session.getCompany()==null){
				throw new Exception("company is null");
			}

			getOrganizeList();
		}catch(Exception ex){
			//			ex.printStackTrace();
			//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
		}
	}

	public void initAdd(){
		try{

			if(session.getCompany()==null){
				throw new Exception("company is null");
			}

			if (this.conversation.isTransient())
			{
				this.conversation.begin();
			}
			model = new DTOChartOfAccount();
			model.setChartOfAccountStatus(true);
			showAdd=true;
			showView=false;
			showEdit=false;
		}catch(Exception ex){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ex.getMessage(),null));
		}
	}
	public void initEdit(){
		try{
			showAdd=false;
			showView=false;
			showEdit=true;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void cancel(){
		try{
			if (!this.conversation.isTransient())
			{
				this.conversation.end();
			}
			model = new DTOChartOfAccount();
			showAdd=false;
			showView=false;
			showEdit=false;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void cancelEdit(){
		try{
			showAdd=false;
			showView=true;
			showEdit=false;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void cancelAdd(){
		try{
			showAdd=false;
			showView=false;
			showEdit=false;
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void clearMainChart(){
		model.setChartOfAccountCodeMain("");
	}

	public void selectedChart(TreeSelectionChangeEvent event){
		try{
			if (this.conversation.isTransient())
			{
				this.conversation.begin();
			}
			List<Object> selection = new ArrayList<Object>(event.getNewSelection());
			if(selection!=null){
				Object currentSelectionKey = selection.get(0);
				UITree tree = (UITree) event.getSource();
				tree.setRowKey(currentSelectionKey);
				DTOChartOfAccount selected = (DTOChartOfAccount) tree.getRowData();

				if(showAdd||showEdit){
					boolean isChild = false;
					boolean isMySelf = false;
					if(model.getChartOfAccountCode()!=null&&model.getChartOfAccountCode().equals(selected.getChartOfAccountCode())){
						isMySelf = true;
					}
					List<DTOChartOfAccount> nodesList = new ArrayList<DTOChartOfAccount>();
					if(model.getChartOfAccountCode()!=null&&!model.getChartOfAccountCode().trim().equals("")){
						checkIsChild(model,nodesList);
					}
					for(DTOChartOfAccount c:nodesList){
						if(selected.getChartOfAccountCode().equals(c.getChartOfAccountCode())){
							isChild = true;
							break;
						}
					}
					if(!isChild&&!isMySelf){
						model.setChartOfAccountCodeMain(selected.getChartOfAccountCode());
					}
				}else{
					this.model = selected;
					showAdd=false;
					showView=true;
					showEdit = false;
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public String update() {
		try {
			conversation.end();
			if(model.getChartOfAccountCode()==null||model.getChartOfAccountCode().trim().length()==0){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, util.getMessage("transaction_save"),null));
				return null;
			}

			model.setCompany(session.getCompany());

			if (showAdd) {
				model.setCreateDateTime(new Date());
				this.chartOfAccountFacade.persist(this.model);
				cancel();			
			} else {
				model.setUpdateDateTime(new Date());
				model.setUpdateBy(session.getUserProfile().getUserLoginName());
				this.chartOfAccountFacade.merge(this.model);
				cancel();	
			}
			getOrganizeList();
		} catch( Exception e ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( e.getMessage() ));
		}
		return "chartOfAccount?faces-redirect=true";
	}

	public void delete() {

		try {
			conversation.end();

			List<DTOChartOfAccount> chartList = new ArrayList<DTOChartOfAccount>();
			for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(model.getChartOfAccountCode(), session.getCompany().getId())){
				chartList.add(ConverterDTO.chartOfAccount(c));
			}

			for(DTOChartOfAccount chart:chartList){
				chart.setChartOfAccountCodeMain(null);
				this.chartOfAccountFacade.merge(chart);
			}
			this.chartOfAccountFacade.remove(this.model);
			cancel();
			initOraganizeTree();
		} catch( Exception e ) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage( e.getMessage() ));
		}
	}

	private void getOrganizeList() throws Exception{
		try{
			rootNodes = new ArrayList<DTOChartOfAccount>();
			List<ChartOfAccount> chartList = chartOfAccountFacade.findRootChartOfAccount(session.getCompany().getId());
			if(chartList!=null&&!chartList.isEmpty()){
				for(ChartOfAccount org:chartList){
					rootNodes.add(ConverterDTO.chartOfAccount(org));
				}
			}
			chartList = chartOfAccountFacade.findInactiveChartOfAccount(session.getCompany().getId());
			if(chartList!=null&&!chartList.isEmpty()){
				for(ChartOfAccount org:chartList){
					rootNodes.add(ConverterDTO.chartOfAccount(org));
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public synchronized List<DTOChartOfAccount> findUnderChart(DTOChartOfAccount rootModel){
		List<DTOChartOfAccount> nodesList = new ArrayList<DTOChartOfAccount>();
		try{
			List<DTOChartOfAccount> orgList = new ArrayList<DTOChartOfAccount>();
			for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(rootModel.getChartOfAccountCode(),session.getCompany().getId())){
				orgList.add(ConverterDTO.chartOfAccount(c));
			}

			if(orgList!=null&&orgList.size()>0){
				for(DTOChartOfAccount org:orgList){
					List<DTOChartOfAccount> orgLastList = new ArrayList<DTOChartOfAccount>();
					for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(org.getChartOfAccountCode(),session.getCompany().getId())){
						orgLastList.add(ConverterDTO.chartOfAccount(c));
					}
					if(orgLastList!=null&&orgLastList.size()>0){
						nodesList.add(org);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return nodesList;
	}

	public synchronized List<DTOChartOfAccount> findUnderLastChart(DTOChartOfAccount rootModel){
		List<DTOChartOfAccount> nodesList = new ArrayList<DTOChartOfAccount>();
		try{
			List<DTOChartOfAccount> orgList = new ArrayList<DTOChartOfAccount>();
			for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(rootModel.getChartOfAccountCode(),session.getCompany().getId())){
				orgList.add(ConverterDTO.chartOfAccount(c));
			}
			if(orgList!=null&&orgList.size()>0){
				for(DTOChartOfAccount org:orgList){
					List<DTOChartOfAccount> orgLastList = new ArrayList<DTOChartOfAccount>();
					for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(org.getChartOfAccountCode(),session.getCompany().getId())){
						orgLastList.add(ConverterDTO.chartOfAccount(c));
					}
					if(orgLastList==null||orgLastList.size()==0){
						nodesList.add(org);
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return nodesList;
	}


	public void checkIsChild(DTOChartOfAccount rootModel,List<DTOChartOfAccount> nodesList){

		try{
			List<DTOChartOfAccount> orgList = new ArrayList<DTOChartOfAccount>();
			for(ChartOfAccount c:chartOfAccountFacade.findUnderChartOfAccount(rootModel.getChartOfAccountCode(),session.getCompany().getId())){
				orgList.add(ConverterDTO.chartOfAccount(c));
			}
			if(orgList!=null&&orgList.size()>0){
				for(DTOChartOfAccount org:orgList){
					nodesList.add(org);
					checkIsChild(org, nodesList);
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}

	}


	public Converter getConverter() {

		return new Converter() {

			@Override
			public Object getAsObject(FacesContext context,
					UIComponent component, String value) {

				return chartOfAccountFacade.findById(Long.valueOf(value));
			}

			@Override
			public String getAsString(FacesContext context,
					UIComponent component, Object value) {

				if (value == null) {
					return "";
				}

				return String.valueOf(((ChartOfAccount) value).getId());
			}
		};
	}


	public synchronized List<DTOChartOfAccount> getRootNodes() {
		if (rootNodes == null) {
			rootNodes = new ArrayList<DTOChartOfAccount>();
		}
		return rootNodes;
	}
	
	public List<ChartOfAccount> getAll()
	{
		return chartOfAccountFacade.getAll();
	}

	public String getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(String selectedNode) {
		this.selectedNode = selectedNode;
	}

	public DTOChartOfAccount getModel() {
		return model;
	}

	public void setModel(DTOChartOfAccount model) {
		this.model = model;
	}

	public boolean isShowAdd() {
		return showAdd;
	}

	public void setShowAdd(boolean showAdd) {
		this.showAdd = showAdd;
	}

	public boolean isShowEdit() {
		return showEdit;
	}

	public void setShowEdit(boolean showEdit) {
		this.showEdit = showEdit;
	}

	public boolean isShowView() {
		return showView;
	}

	public void setShowView(boolean showView) {
		this.showView = showView;
	}

	public List<Currency> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<Currency> currencyList) {
		this.currencyList = currencyList;
	}

}