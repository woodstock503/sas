package com.lao.saacplus.util;

import javax.ejb.Stateless;

import com.lao.saacplus.dto.DTOAccountBalance;
import com.lao.saacplus.dto.DTOAccountBook;
import com.lao.saacplus.dto.DTOAccountType;
import com.lao.saacplus.dto.DTOAccountYear;
import com.lao.saacplus.dto.DTOBranch;
import com.lao.saacplus.dto.DTOChartOfAccount;
import com.lao.saacplus.dto.DTOCompany;
import com.lao.saacplus.dto.DTOCurrency;
import com.lao.saacplus.dto.DTODepartment;
import com.lao.saacplus.dto.DTOPeriod;
import com.lao.saacplus.dto.DTOProgram;
import com.lao.saacplus.dto.DTORate;
import com.lao.saacplus.dto.DTORole;
import com.lao.saacplus.dto.DTOTransactionDetail;
import com.lao.saacplus.dto.DTOTransactionMaster;
import com.lao.saacplus.dto.DTOUserProfile;
import com.lao.saacplus.entity.AccountBalance;
import com.lao.saacplus.entity.AccountBook;
import com.lao.saacplus.entity.AccountType;
import com.lao.saacplus.entity.AccountYear;
import com.lao.saacplus.entity.Branch;
import com.lao.saacplus.entity.ChartOfAccount;
import com.lao.saacplus.entity.Company;
import com.lao.saacplus.entity.Currency;
import com.lao.saacplus.entity.Department;
import com.lao.saacplus.entity.Period;
import com.lao.saacplus.entity.Program;
import com.lao.saacplus.entity.Rate;
import com.lao.saacplus.entity.Role;
import com.lao.saacplus.entity.TransactionDetail;
import com.lao.saacplus.entity.TransactionMaster;
import com.lao.saacplus.entity.UserProfile;


@Stateless
public class ConverterDTO {


	/** AccountBalance **/
	public static DTOAccountBalance accountBalance(AccountBalance accountBalance){
		DTOAccountBalance model = new DTOAccountBalance();
		model.setId(accountBalance.getId());
		model.setDebitBalance(accountBalance.getDebitBalance());
		model.setCreditBalance(accountBalance.getCreditBalance());
		model.setDescription(accountBalance.getDescription());
		model.setDescriptionEn(accountBalance.getDescriptionEn());
		model.setChartOfAccount(accountBalance.getChartOfAccount());
		model.setCompany(accountBalance.getCompany());
		model.setBalanceAccountYear(accountBalance.getBalanceAccountYear());
		model.setOpeningAccountYear(accountBalance.getOpeningAccountYear());
		return model;
	}
	public static AccountBalance accountBalance(DTOAccountBalance accountBalance,AccountBalance model){
		model.setId(accountBalance.getId());
		model.setDebitBalance(accountBalance.getDebitBalance());
		model.setCreditBalance(accountBalance.getCreditBalance());
		model.setDescription(accountBalance.getDescription());
		model.setDescriptionEn(accountBalance.getDescriptionEn());
		model.setChartOfAccount(accountBalance.getChartOfAccount());
		model.setCompany(accountBalance.getCompany());
		model.setBalanceAccountYear(accountBalance.getBalanceAccountYear());
		model.setOpeningAccountYear(accountBalance.getOpeningAccountYear());
		return model;
	}



	/** AccountBook **/
	public static DTOAccountBook accountBook(AccountBook accountBook){
		DTOAccountBook model = new DTOAccountBook();
		model.setAccountBookCode(accountBook.getAccountBookCode());
		model.setAccountBookName(accountBook.getAccountBookName());
		model.setAccountBookNameEn(accountBook.getAccountBookNameEn());
		model.setAccountBookStatus(accountBook.getAccountBookStatus());
		model.setCreateBy(accountBook.getCreateBy());
		model.setCreateDateTime(accountBook.getCreateDateTime());
		model.setDescription(accountBook.getDescription());
		model.setId(accountBook.getId());
		model.setUpdateBy(accountBook.getUpdateBy());
		model.setUpdateDateTime(accountBook.getUpdateDateTime());
		return model;
	}
	public static AccountBook accountBook(DTOAccountBook accountBook,AccountBook model){
		model.setAccountBookCode(accountBook.getAccountBookCode());
		model.setAccountBookName(accountBook.getAccountBookName());
		model.setAccountBookNameEn(accountBook.getAccountBookNameEn());
		model.setAccountBookStatus(accountBook.isAccountBookStatus());
		model.setCreateBy(accountBook.getCreateBy());
		model.setCreateDateTime(accountBook.getCreateDateTime());
		model.setDescription(accountBook.getDescription());
		model.setId(accountBook.getId());
		model.setUpdateBy(accountBook.getUpdateBy());
		model.setUpdateDateTime(accountBook.getUpdateDateTime());
		return model;
	}



	/** AccountType **/
	public static DTOAccountType accountType(AccountType accountType){
		DTOAccountType model = new DTOAccountType();
		model.setAccountTypeName(accountType.getAccountTypeName());
		model.setAccountTypeNameEn(accountType.getAccountTypeNameEn());
		model.setAccountTypeStatus(accountType.getAccountTypeStatus());
		model.setCreateBy(accountType.getCreateBy());
		model.setCreateDateTime(accountType.getCreateDateTime());
		model.setDescription(accountType.getDescription());
		model.setId(accountType.getId());
		model.setUpdateBy(accountType.getUpdateBy());
		model.setUpdateDateTime(accountType.getUpdateDateTime());
		return model;
	}
	public static AccountType accountType(DTOAccountType accountType,AccountType model){
		model.setAccountTypeName(accountType.getAccountTypeName());
		model.setAccountTypeNameEn(accountType.getAccountTypeNameEn());
		model.setAccountTypeStatus(accountType.isAccountTypeStatus());
		model.setCreateBy(accountType.getCreateBy());
		model.setCreateDateTime(accountType.getCreateDateTime());
		model.setDescription(accountType.getDescription());
		model.setId(accountType.getId());
		model.setUpdateBy(accountType.getUpdateBy());
		model.setUpdateDateTime(accountType.getUpdateDateTime());
		return model;
	}



	/** AccountYear **/
	public static DTOAccountYear accountYear(AccountYear accountYear){
		DTOAccountYear model = new DTOAccountYear();
		model.setId(accountYear.getId());
		model.setStartDate(accountYear.getStartDate());
		model.setEndDate(accountYear.getEndDate());
		model.setDebitBalance(accountYear.getDebitBalance());
		model.setCreditBalance(accountYear.getCreditBalance());
		model.setCloseStatus(accountYear.getCloseStatus());
		model.setCompany(accountYear.getCompany());
		model.setCreateBy(accountYear.getCreateBy());
		model.setCreateDateTime(accountYear.getCreateDateTime());
		model.setUpdateBy(accountYear.getUpdateBy());
		model.setUpdateDateTime(accountYear.getUpdateDateTime());
		model.setAccountYearStatus(accountYear.getAccountYearStatus());
		model.setAccountBalances(accountYear.getAccountBalances());
		return model;
	}
	public static AccountYear accountYear(DTOAccountYear accountYear,AccountYear model){
		model.setId(accountYear.getId());
		model.setStartDate(accountYear.getStartDate());
		model.setEndDate(accountYear.getEndDate());
		model.setDebitBalance(accountYear.getDebitBalance());
		model.setCreditBalance(accountYear.getCreditBalance());
		model.setCloseStatus(accountYear.isCloseStatus());
		model.setCompany(accountYear.getCompany());
		model.setCreateBy(accountYear.getCreateBy());
		model.setCreateDateTime(accountYear.getCreateDateTime());
		model.setUpdateBy(accountYear.getUpdateBy());
		model.setUpdateDateTime(accountYear.getUpdateDateTime());
		model.setAccountYearStatus(accountYear.isAccountYearStatus());
		model.setAccountBalances(accountYear.getAccountBalances());
		return model;
	}



	/** Branch **/
	public static DTOBranch branch(Branch branch){
		DTOBranch model = new DTOBranch();
		model.setId(branch.getId());
		model.setBranchName(branch.getBranchName());
		model.setBranchNameEn(branch.getBranchNameEn());
		model.setCreateBy(branch.getCreateBy());
		model.setCreateDateTime(branch.getCreateDateTime());
		model.setUpdateBy(branch.getUpdateBy());
		model.setUpdateDateTime(branch.getUpdateDateTime());
		model.setBranchStatus(branch.getBranchStatus());
		model.setAddress(branch.getAddress());
		model.setTelephone(branch.getTelephone());
		model.setFax(branch.getFax());
		model.setEmail(branch.getEmail());
		model.setVision(branch.getVision());
		model.setMission(branch.getMission());
		model.setLogo(branch.getLogo());
		model.setTaxId(branch.getTaxId());
		model.setEstablishDateTime(branch.getEstablishDateTime());
		model.setCompany(branch.getCompany());
		return model;
	}
	public static Branch branch(DTOBranch branch,Branch model){
		model.setId(branch.getId());
		model.setBranchName(branch.getBranchName());
		model.setBranchNameEn(branch.getBranchNameEn());
		model.setCreateBy(branch.getCreateBy());
		model.setCreateDateTime(branch.getCreateDateTime());
		model.setUpdateBy(branch.getUpdateBy());
		model.setUpdateDateTime(branch.getUpdateDateTime());
		model.setBranchStatus(branch.isBranchStatus());
		model.setAddress(branch.getAddress());
		model.setTelephone(branch.getTelephone());
		model.setFax(branch.getFax());
		model.setEmail(branch.getEmail());
		model.setVision(branch.getVision());
		model.setMission(branch.getMission());
		model.setLogo(branch.getLogo());
		model.setTaxId(branch.getTaxId());
		model.setEstablishDateTime(branch.getEstablishDateTime());
		model.setCompany(branch.getCompany());
		return model;
	}



	/** ChartOfAccount **/
	public static DTOChartOfAccount chartOfAccount(ChartOfAccount chartOfAccount){
		DTOChartOfAccount model = new DTOChartOfAccount();
		model.setAccountType(chartOfAccount.getAccountType());
		model.setChartOfAccountCode(chartOfAccount.getChartOfAccountCode());
		model.setChartOfAccountCodeMain(chartOfAccount.getChartOfAccountCodeMain());
		model.setChartOfAccountName(chartOfAccount.getChartOfAccountName());
		model.setChartOfAccountNameEn(chartOfAccount.getChartOfAccountNameEn());
		model.setChartOfAccountStatus(chartOfAccount.isChartOfAccountStatus());
		model.setCompany(chartOfAccount.getCompany());
		model.setCreateBy(chartOfAccount.getCreateBy());
		model.setCreateDateTime(chartOfAccount.getCreateDateTime());
		model.setCurrency(chartOfAccount.getCurrency());
		model.setId(chartOfAccount.getId());
		model.setUpdateBy(chartOfAccount.getUpdateBy());
		model.setUpdateDateTime(chartOfAccount.getUpdateDateTime());
		return model;
	}
	public static ChartOfAccount chartOfAccount(DTOChartOfAccount chartOfAccount,ChartOfAccount model){
		model.setAccountType(chartOfAccount.getAccountType());
		model.setChartOfAccountCode(chartOfAccount.getChartOfAccountCode());
		model.setChartOfAccountCodeMain(chartOfAccount.getChartOfAccountCodeMain());
		model.setChartOfAccountName(chartOfAccount.getChartOfAccountName());
		model.setChartOfAccountNameEn(chartOfAccount.getChartOfAccountNameEn());
		model.setChartOfAccountStatus(chartOfAccount.isChartOfAccountStatus());
		model.setCompany(chartOfAccount.getCompany());
		model.setCreateBy(chartOfAccount.getCreateBy());
		model.setCreateDateTime(chartOfAccount.getCreateDateTime());
		model.setCurrency(chartOfAccount.getCurrency());
		model.setId(chartOfAccount.getId());
		model.setUpdateBy(chartOfAccount.getUpdateBy());
		model.setUpdateDateTime(chartOfAccount.getUpdateDateTime());
		return model;
	}



	/** Company **/
	public static DTOCompany company(Company company){
		DTOCompany model = new DTOCompany();
		model.setId(company.getId());
		model.setCompanyName(company.getCompanyName());
		model.setCompanyNameEn(company.getCompanyNameEn());
		model.setCreateBy(company.getCreateBy());
		model.setCreateDateTime(company.getCreateDateTime());
		model.setUpdateBy(company.getUpdateBy());
		model.setUpdateDateTime(company.getUpdateDateTime());
		model.setCompanyStatus(company.getCompanyStatus());
		model.setCurrency(company.getCurrency());
		model.setDigitPattern(company.getDigitPattern());
		model.setStartDate(company.getStartDate());
		model.setEndDate(company.getEndDate());
		model.setPeriod(company.getPeriod());
		model.setAddress(company.getAddress());
		model.setTelephone(company.getTelephone());
		model.setFax(company.getFax());
		model.setEmail(company.getEmail());
		model.setVision(company.getVision());
		model.setMission(company.getMission());
		model.setLogo(company.getLogo());
		model.setTaxId(company.getTaxId());
		model.setEstablishDateTime(company.getEstablishDateTime());
		return model;
	}
	public static Company company(DTOCompany company,Company model){
		model.setId(company.getId());
		model.setCompanyName(company.getCompanyName());
		model.setCompanyNameEn(company.getCompanyNameEn());
		model.setCreateBy(company.getCreateBy());
		model.setCreateDateTime(company.getCreateDateTime());
		model.setUpdateBy(company.getUpdateBy());
		model.setUpdateDateTime(company.getUpdateDateTime());
		model.setCompanyStatus(company.isCompanyStatus());
		model.setCurrency(company.getCurrency());
		model.setDigitPattern(company.getDigitPattern());
		model.setStartDate(company.getStartDate());
		model.setEndDate(company.getEndDate());
		model.setPeriod(company.getPeriod());
		model.setAddress(company.getAddress());
		model.setTelephone(company.getTelephone());
		model.setFax(company.getFax());
		model.setEmail(company.getEmail());
		model.setVision(company.getVision());
		model.setMission(company.getMission());
		model.setLogo(company.getLogo());
		model.setTaxId(company.getTaxId());
		model.setEstablishDateTime(company.getEstablishDateTime());
		return model;
	}



	/** Currency **/
	public static DTOCurrency currency(Currency currency){
		DTOCurrency model = new DTOCurrency();
		model.setAbbreviation(currency.getAbbreviation());
		model.setCountry(currency.getCountry());
		model.setCountryEn(currency.getCountryEn());
		model.setCreateBy(currency.getCreateBy());
		model.setCreateDateTime(currency.getCreateDateTime());
		model.setCurrencyStatus(currency.getCurrencyStatus());
		model.setId(currency.getId());
		model.setUpdateBy(currency.getUpdateBy());
		model.setUpdateDateTime(currency.getUpdateDateTime());
		return model;
	}
	public static Currency currency(DTOCurrency currency,Currency model){
		model.setAbbreviation(currency.getAbbreviation());
		model.setCountry(currency.getCountry());
		model.setCountryEn(currency.getCountryEn());
		model.setCreateBy(currency.getCreateBy());
		model.setCreateDateTime(currency.getCreateDateTime());
		model.setCurrencyStatus(currency.isCurrencyStatus());
		model.setId(currency.getId());
		model.setUpdateBy(currency.getUpdateBy());
		model.setUpdateDateTime(currency.getUpdateDateTime());
		return model;
	}



	/** Department **/
	public static DTODepartment department(Department department){
		DTODepartment model = new DTODepartment();
		model.setId(department.getId());
		model.setDepartmentName(department.getDepartmentName());
		model.setDepartmentNameEn(department.getDepartmentNameEn());
		model.setCreateBy(department.getCreateBy());
		model.setCreateDateTime(department.getCreateDateTime());
		model.setUpdateBy(department.getUpdateBy());
		model.setUpdateDateTime(department.getUpdateDateTime());
		model.setDepartmentStatus(department.getDepartmentStatus());
		model.setCompany(department.getCompany());
		return model;
	}
	public static Department department(DTODepartment department,Department model){
		model.setId(department.getId());
		model.setDepartmentName(department.getDepartmentName());
		model.setDepartmentNameEn(department.getDepartmentNameEn());
		model.setCreateBy(department.getCreateBy());
		model.setCreateDateTime(department.getCreateDateTime());
		model.setUpdateBy(department.getUpdateBy());
		model.setUpdateDateTime(department.getUpdateDateTime());
		model.setDepartmentStatus(department.isDepartmentStatus());
		model.setCompany(department.getCompany());
		return model;
	}



	/** Period **/
	public static DTOPeriod period(Period period){
		DTOPeriod model = new DTOPeriod();
		model.setAccountYear(period.getAccountYear());
		model.setCloseStatus(period.getCloseStatus());
		model.setCompany(period.getCompany());
		model.setEndDate(period.getEndDate());
		model.setId(period.getId());
		model.setPeriodNo(period.getPeriodNo());
		model.setStartDate(period.getStartDate());
		return model;
	}
	public static Period period(DTOPeriod period,Period model){
		model.setAccountYear(period.getAccountYear());
		model.setCloseStatus(period.isCloseStatus());
		model.setCompany(period.getCompany());
		model.setEndDate(period.getEndDate());
		model.setId(period.getId());
		model.setPeriodNo(period.getPeriodNo());
		model.setStartDate(period.getStartDate());
		return model;
	}


	/** Program **/
	public static DTOProgram program(Program program){
		DTOProgram model = new DTOProgram();
		model.setId(program.getId());
		model.setProgramName(program.getProgramName());
		model.setProgramNameEn(program.getProgramNameEn());
		model.setCreateBy(program.getCreateBy());
		model.setCreateDateTime(program.getCreateDateTime());
		model.setUpdateBy(program.getUpdateBy());
		model.setUpdateDateTime(program.getUpdateDateTime());
		model.setProgramStatus(program.getProgramStatus());
		model.setUrl(program.getUrl());
		model.setDescription(program.getDescription());
		model.setProgramRoles(program.getProgramRoles());
		return model;
	}
	public static Program program(DTOProgram program,Program model){
		model.setId(program.getId());
		model.setProgramName(program.getProgramName());
		model.setProgramNameEn(program.getProgramNameEn());
		model.setCreateBy(program.getCreateBy());
		model.setCreateDateTime(program.getCreateDateTime());
		model.setUpdateBy(program.getUpdateBy());
		model.setUpdateDateTime(program.getUpdateDateTime());
		model.setProgramStatus(program.isProgramStatus());
		model.setUrl(program.getUrl());
		model.setDescription(program.getDescription());
		model.setProgramRoles(program.getProgramRoles());
		return model;
	}


	/** Rate **/
	public static DTORate rate(Rate rate){
		DTORate model = new DTORate();
		model.setId(rate.getId());
		model.setRateDate(rate.getRateDate());
		model.setCurrency(rate.getCurrency());
		model.setRate(rate.getRate());
		model.setCreateBy(rate.getCreateBy());
		model.setCreateDateTime(rate.getCreateDateTime());
		model.setUpdateBy(rate.getUpdateBy());
		model.setUpdateDateTime(rate.getUpdateDateTime());
		model.setRateStatus(rate.getRateStatus());
		return model;
	}
	public static Rate rate(DTORate rate,Rate model){
		model.setId(rate.getId());
		model.setRateDate(rate.getRateDate());
		model.setCurrency(rate.getCurrency());
		model.setRate(rate.getRate());
		model.setCreateBy(rate.getCreateBy());
		model.setCreateDateTime(rate.getCreateDateTime());
		model.setUpdateBy(rate.getUpdateBy());
		model.setUpdateDateTime(rate.getUpdateDateTime());
		model.setRateStatus(rate.isRateStatus());
		return model;
	}


	/** Role **/
	public static DTORole role(Role role){
		DTORole model = new DTORole();
		model.setId(role.getId());
		model.setRoleName(role.getRoleName());
		model.setRoleNameEn(role.getRoleNameEn());
		model.setCreateBy(role.getCreateBy());
		model.setCreateDateTime(role.getCreateDateTime());
		model.setUpdateBy(role.getUpdateBy());
		model.setUpdateDateTime(role.getUpdateDateTime());
		model.setRoleStatus(role.getRoleStatus());
		return model;
	}
	public static Role role(DTORole role,Role model){
		model.setId(role.getId());
		model.setRoleName(role.getRoleName());
		model.setRoleNameEn(role.getRoleNameEn());
		model.setCreateBy(role.getCreateBy());
		model.setCreateDateTime(role.getCreateDateTime());
		model.setUpdateBy(role.getUpdateBy());
		model.setUpdateDateTime(role.getUpdateDateTime());
		model.setRoleStatus(role.isRoleStatus());
		return model;
	}


	/** TransactionDetail **/
	public static DTOTransactionDetail transactionDetail(TransactionDetail transactionDetail){
		DTOTransactionDetail model = new DTOTransactionDetail();
		model.setId(transactionDetail.getId());
		model.setRate(transactionDetail.getRate());
		model.setTransactionMaster(transactionDetail.getTransactionMaster());
		model.setChartOfAccount(transactionDetail.getChartOfAccount());
		model.setAccountType(transactionDetail.getAccountType());
		model.setDepartment(transactionDetail.getDepartment());
		model.setCompany(transactionDetail.getCompany());
		model.setBranch(transactionDetail.getBranch());
		model.setAccountYear(transactionDetail.getAccountYear());
		model.setAccountBook(transactionDetail.getAccountBook());
		model.setAmountDebit(transactionDetail.getAmountDebit());
		model.setAmountDebitLo(transactionDetail.getAmountDebitLo());
		model.setAmountCredit(transactionDetail.getAmountCredit());
		model.setAmountCreditLo(transactionDetail.getAmountCreditLo());
		model.setTransactionRate(transactionDetail.getTransactionRate());
		model.setCreateBy(transactionDetail.getCreateBy());
		model.setCreateDateTime(transactionDetail.getCreateDateTime());
		model.setUpdateBy(transactionDetail.getUpdateBy());
		model.setUpdateDateTime(transactionDetail.getUpdateDateTime());
		model.setTransactionDetailStatus(transactionDetail.getTransactionDetailStatus());
		model.setPeriod(transactionDetail.getPeriod());
		model.setPeriodNo(transactionDetail.getPeriodNo());
		model.setDescription(transactionDetail.getDescription());
		model.setDescriptionEn(transactionDetail.getDescriptionEn());
		model.setOrderNo(transactionDetail.getOrderNo());
		return model;
	}
	public static TransactionDetail transactionDetail(DTOTransactionDetail transactionDetail,TransactionDetail model){
		model.setId(transactionDetail.getId());
		model.setRate(transactionDetail.getRate());
		model.setTransactionMaster(transactionDetail.getTransactionMaster());
		model.setChartOfAccount(transactionDetail.getChartOfAccount());
		model.setAccountType(transactionDetail.getAccountType());
		model.setDepartment(transactionDetail.getDepartment());
		model.setCompany(transactionDetail.getCompany());
		model.setBranch(transactionDetail.getBranch());
		model.setAccountYear(transactionDetail.getAccountYear());
		model.setAccountBook(transactionDetail.getAccountBook());
		model.setAmountDebit(transactionDetail.getAmountDebit());
		model.setAmountDebitLo(transactionDetail.getAmountDebitLo());
		model.setAmountCredit(transactionDetail.getAmountCredit());
		model.setAmountCreditLo(transactionDetail.getAmountCreditLo());
		model.setTransactionRate(transactionDetail.getTransactionRate());
		model.setCreateBy(transactionDetail.getCreateBy());
		model.setCreateDateTime(transactionDetail.getCreateDateTime());
		model.setUpdateBy(transactionDetail.getUpdateBy());
		model.setUpdateDateTime(transactionDetail.getUpdateDateTime());
		model.setTransactionDetailStatus(transactionDetail.isTransactionDetailStatus());
		model.setPeriod(transactionDetail.getPeriod());
		model.setPeriodNo(transactionDetail.getPeriodNo());
		model.setDescription(transactionDetail.getDescription());
		model.setDescriptionEn(transactionDetail.getDescriptionEn());
		model.setOrderNo(transactionDetail.getOrderNo());
		return model;
	}


	/** TransactionMaster **/
	public static DTOTransactionMaster transactionMaster(TransactionMaster transactionMaster){
		DTOTransactionMaster model = new DTOTransactionMaster();
		model.setId(transactionMaster.getId());
		model.setCompany(transactionMaster.getCompany());
		model.setCreateUser(transactionMaster.getCreateUser());
		model.setBranch(transactionMaster.getBranch());
		model.setAccountYear(transactionMaster.getAccountYear());
		model.setAccountBook(transactionMaster.getAccountBook());
		model.setBillDate(transactionMaster.getBillDate());
		model.setCertifyNo(transactionMaster.getCertifyNo());
		model.setReferenceNo(transactionMaster.getReferenceNo());
		model.setDescription(transactionMaster.getDescription());
		model.setDescriptionEn(transactionMaster.getDescriptionEn());
		model.setCreateBy(transactionMaster.getCreateBy());
		model.setCreateDateTime(transactionMaster.getCreateDateTime());
		model.setUpdateBy(transactionMaster.getUpdateBy());
		model.setUpdateDateTime(transactionMaster.getUpdateDateTime());
		model.setTransactionMasterStatus(transactionMaster.getTransactionMasterStatus());
		model.setTransactionDetails(transactionMaster.getTransactionDetails());
		model.setTotalAmountDebit(transactionMaster.getTotalAmountDebit());
		model.setTotalAmountDebitLo(transactionMaster.getTotalAmountDebitLo());
		model.setTotalAmountCredit(transactionMaster.getTotalAmountCredit());
		model.setTotalAmountCreditLo(transactionMaster.getTotalAmountCreditLo());
		model.setPeriod(transactionMaster.getPeriod());
		model.setPeriodNo(transactionMaster.getPeriodNo());
		return model;
	}
	public static TransactionMaster transactionMaster(DTOTransactionMaster transactionMaster,TransactionMaster model){
		model.setId(transactionMaster.getId());
		model.setCompany(transactionMaster.getCompany());
		model.setCreateUser(transactionMaster.getCreateUser());
		model.setBranch(transactionMaster.getBranch());
		model.setAccountYear(transactionMaster.getAccountYear());
		model.setAccountBook(transactionMaster.getAccountBook());
		model.setBillDate(transactionMaster.getBillDate());
		model.setCertifyNo(transactionMaster.getCertifyNo());
		model.setReferenceNo(transactionMaster.getReferenceNo());
		model.setDescription(transactionMaster.getDescription());
		model.setDescriptionEn(transactionMaster.getDescriptionEn());
		model.setCreateBy(transactionMaster.getCreateBy());
		model.setCreateDateTime(transactionMaster.getCreateDateTime());
		model.setUpdateBy(transactionMaster.getUpdateBy());
		model.setUpdateDateTime(transactionMaster.getUpdateDateTime());
		model.setTransactionMasterStatus(transactionMaster.isTransactionMasterStatus());
		model.setTransactionDetails(transactionMaster.getTransactionDetails());
		model.setTotalAmountDebit(transactionMaster.getTotalAmountDebit());
		model.setTotalAmountDebitLo(transactionMaster.getTotalAmountDebitLo());
		model.setTotalAmountCredit(transactionMaster.getTotalAmountCredit());
		model.setTotalAmountCreditLo(transactionMaster.getTotalAmountCreditLo());
		model.setPeriod(transactionMaster.getPeriod());
		model.setPeriodNo(transactionMaster.getPeriodNo());
		return model;
	}



	/** UserProfile **/
	public static DTOUserProfile userProfile(UserProfile userProfile){
		DTOUserProfile model = new DTOUserProfile();
		model.setId(userProfile.getId());
		model.setUserLoginName(userProfile.getUserLoginName());
		model.setUserFullName(userProfile.getUserFullName());
		model.setUserFullNameEn(userProfile.getUserFullNameEn());
		model.setUserPassword(userProfile.getUserPassword());
		model.setEmail(userProfile.getEmail());
		model.setUserStatus(userProfile.isUserStatus());
		model.setCreateBy(userProfile.getCreateBy());
		model.setCreateDateTime(userProfile.getCreateDateTime());
		model.setUpdateBy(userProfile.getUpdateBy());
		model.setUpdateDateTime(userProfile.getUpdateDateTime());
		model.setLastLoginDateTime(userProfile.getLastLoginDateTime());
		model.setLastChangePwdDateTime(userProfile.getLastChangePwdDateTime());
		model.setLastResetPwdDateTime(userProfile.getLastResetPwdDateTime());
		model.setInActiveReason(userProfile.getInActiveReason());
		model.setForceChangePwd(userProfile.isForceChangePwd());
		model.setExpiredFlag(userProfile.isExpiredFlag());
		model.setExpireDate(userProfile.getExpireDate());
		model.setUserRoles(userProfile.getUserRoles());
		model.setUserCompanys(userProfile.getUserCompanys());
		model.setUserBranchs(userProfile.getUserBranchs());
		return model;
	}
	public static UserProfile userProfile(DTOUserProfile userProfile,UserProfile model){
		model.setId(userProfile.getId());
		model.setUserLoginName(userProfile.getUserLoginName());
		model.setUserFullName(userProfile.getUserFullName());
		model.setUserFullNameEn(userProfile.getUserFullNameEn());
		model.setUserPassword(userProfile.getUserPassword());
		model.setEmail(userProfile.getEmail());
		model.setUserStatus(userProfile.isUserStatus());
		model.setCreateBy(userProfile.getCreateBy());
		model.setCreateDateTime(userProfile.getCreateDateTime());
		model.setUpdateBy(userProfile.getUpdateBy());
		model.setUpdateDateTime(userProfile.getUpdateDateTime());
		model.setLastLoginDateTime(userProfile.getLastLoginDateTime());
		model.setLastChangePwdDateTime(userProfile.getLastChangePwdDateTime());
		model.setLastResetPwdDateTime(userProfile.getLastResetPwdDateTime());
		model.setInActiveReason(userProfile.getInActiveReason());
		model.setForceChangePwd(userProfile.isForceChangePwd());
		model.setExpiredFlag(userProfile.isExpiredFlag());
		model.setExpireDate(userProfile.getExpireDate());
		model.setUserRoles(userProfile.getUserRoles());
		model.setUserCompanys(userProfile.getUserCompanys());
		model.setUserBranchs(userProfile.getUserBranchs());
		return model;
	}
}
