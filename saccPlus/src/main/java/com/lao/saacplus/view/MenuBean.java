package com.lao.saacplus.view;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;


@Named
@RequestScoped
public class MenuBean {
	
	@Inject
	private Conversation conversation;
	
	public String rateMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/rate/search?faces-redirect=true" ;
	}

	public String currencyMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/currency/search?faces-redirect=true" ;
	}
	

	public String chartOfAccountMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/chartOfAccount?faces-redirect=true" ;
	}
	
	public String journalTransactionMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/journalTransaction?faces-redirect=true";
	}
	
	public String journalTransactionEditMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/transactionMaster/search?faces-redirect=true";
	}
	
	public String journalTransactionDetailEditMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/transactionDetail/search?faces-redirect=true";
	}
	
	public String journalTransactionOpeningMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/accountBalance/search?faces-redirect=true";
	}
	
	public String journalTransactionFirstOpeningMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/openingAccountBalance?faces-redirect=true";
	}
	
	public String accountBookMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/accountBook/search?faces-redirect=true";
	}
	
	public String accountTypeMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/accountType/search?faces-redirect=true";
	}
	
	public String accountYearMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/accountYear/search?faces-redirect=true";
	}
	
	public String userProfileMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/userProfile/search?faces-redirect=true";
	}
	
	public String branchMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/branch/search?faces-redirect=true";
	}
	
	public String companyMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/company/search?faces-redirect=true";
	}
	
	public String departmentMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/department/search?faces-redirect=true";
	}
	
	public String programMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/program/search?faces-redirect=true";
	}

	public String periodMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/period/search?faces-redirect=true";
	}
	
	public String roleMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/role/search?faces-redirect=true";
	}
	
	public String indexMenu(){
		if (!this.conversation.isTransient())
		{
			this.conversation.end();
		}
		return "/view/index?faces-redirect=true";
	}
}
