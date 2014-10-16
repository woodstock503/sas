package com.lao.saacplus.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;

import com.lao.saacplus.entity.Currency;
import javax.persistence.ManyToOne;
import com.lao.saacplus.entity.AccountType;
import com.lao.saacplus.entity.Company;

@Entity
public class ChartOfAccount implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -4518675469451009379L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String chartOfAccountCode;

   @Column
   private String chartOfAccountCodeMain;

   @Column
   private String chartOfAccountName;

   @Column
   private String chartOfAccountNameEn;

   @ManyToOne
   private Currency currency;

   @Column
   private boolean chartOfAccountStatus;

   @ManyToOne
   private AccountType accountType;

   @ManyToOne
   private Company company;

   @Column
   private String createBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createDateTime;

   @Column
   private String updateBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDateTime;

   public Long getId()
   {
      return this.id;
   }

   public void setId(final Long id)
   {
      this.id = id;
   }

   public int getVersion()
   {
      return this.version;
   }

   public void setVersion(final int version)
   {
      this.version = version;
   }

   @Override
   public boolean equals(Object that)
   {
      if (this == that)
      {
         return true;
      }
      if (that == null)
      {
         return false;
      }
      if (getClass() != that.getClass())
      {
         return false;
      }
      if (id != null)
      {
         return id.equals(((ChartOfAccount) that).id);
      }
      return super.equals(that);
   }

   @Override
   public int hashCode()
   {
      if (id != null)
      {
         return id.hashCode();
      }
      return super.hashCode();
   }

   public String getChartOfAccountCode()
   {
      return this.chartOfAccountCode;
   }

   public void setChartOfAccountCode(final String chartOfAccountCode)
   {
      this.chartOfAccountCode = chartOfAccountCode;
   }

   public String getChartOfAccountName()
   {
      return this.chartOfAccountName;
   }

   public void setChartOfAccountName(final String chartOfAccountName)
   {
      this.chartOfAccountName = chartOfAccountName;
   }

   public String getChartOfAccountNameEn()
   {
      return chartOfAccountNameEn;
   }

   public void setChartOfAccountNameEn(String chartOfAccountNameEn)
   {
      this.chartOfAccountNameEn = chartOfAccountNameEn;
   }

   public boolean isChartOfAccountStatus()
   {
      return chartOfAccountStatus;
   }

   public void setChartOfAccountStatus(boolean chartOfAccountStatus)
   {
      this.chartOfAccountStatus = chartOfAccountStatus;
   }

   public String getChartOfAccountCodeMain()
   {
      return chartOfAccountCodeMain;
   }

   public void setChartOfAccountCodeMain(String chartOfAccountCodeMain)
   {
      this.chartOfAccountCodeMain = chartOfAccountCodeMain;
   }

   public String getCreateBy()
   {
      return createBy;
   }

   public void setCreateBy(final String createBy)
   {
      this.createBy = createBy;
   }

   public Date getCreateDateTime()
   {
      return createDateTime;
   }

   public void setCreateDateTime(Date createDateTime)
   {
      this.createDateTime = createDateTime;
   }

   public String getUpdateBy()
   {
      return updateBy;
   }

   public void setUpdateBy(String updateBy)
   {
      this.updateBy = updateBy;
   }

   public Date getUpdateDateTime()
   {
      return updateDateTime;
   }

   public void setUpdateDateTime(Date updateDateTime)
   {
      this.updateDateTime = updateDateTime;
   }

   @Override
   public String toString()
   {
      //      String result = getClass().getSimpleName() + " ";
      //      if (chartOfAccountCode != null && !chartOfAccountCode.trim().isEmpty())
      //         result += "chartOfAccountCode: " + chartOfAccountCode;
      //      if (chartOfAccountName != null && !chartOfAccountName.trim().isEmpty())
      //         result += ", chartOfAccountName: " + chartOfAccountName;
      //      if (chartOfAccountNameEn != null && !chartOfAccountNameEn.trim().isEmpty())
      //         result += ", chartOfAccountNameEn: " + chartOfAccountNameEn;
      //      if (chartOfAccountCodeMain != null && !chartOfAccountCodeMain.trim().isEmpty())
      //         result += ", chartOfAccountCodeMain: " + chartOfAccountCodeMain;
      String result = chartOfAccountCode + " " + chartOfAccountName;
      return result;
   }

   public Currency getCurrency()
   {
      return this.currency;
   }

   public void setCurrency(final Currency currency)
   {
      this.currency = currency;
   }

   public AccountType getAccountType()
   {
      return this.accountType;
   }

   public void setAccountType(final AccountType accountType)
   {
      this.accountType = accountType;
   }

   public Company getCompany()
   {
      return this.company;
   }

   public void setCompany(final Company company)
   {
      this.company = company;
   }
}