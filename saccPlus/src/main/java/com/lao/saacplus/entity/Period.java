package com.lao.saacplus.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.lao.saacplus.entity.AccountYear;

@Entity
public class Period implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 7995318627341462713L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Temporal(TemporalType.TIMESTAMP)
   private Date startDate;

   @Temporal(TemporalType.TIMESTAMP)
   private Date endDate;

   @ManyToOne
   private Company company;

   @Column
   private int periodNo;

   @Column
   private boolean closeStatus;

   @ManyToOne
   private AccountYear accountYear;

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
         return id.equals(((Period) that).id);
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

   public Date getStartDate()
   {
      return this.startDate;
   }

   public void setStartDate(final Date startDate)
   {
      this.startDate = startDate;
   }

   public Date getEndDate()
   {
      return this.endDate;
   }

   public void setEndDate(final Date endDate)
   {
      this.endDate = endDate;
   }

   public Company getCompany()
   {
      return company;
   }

   public void setCompany(Company company)
   {
      this.company = company;
   }

   public int getPeriodNo()
   {
      return this.periodNo;
   }

   public void setPeriodNo(final int periodNo)
   {
      this.periodNo = periodNo;
   }

   public boolean getCloseStatus()
   {
      return this.closeStatus;
   }

   public void setCloseStatus(final boolean closeStatus)
   {
      this.closeStatus = closeStatus;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "serialVersionUID: " + serialVersionUID;
      result += ", periodNo: " + periodNo;
      result += ", closeStatus: " + closeStatus;
      return result;
   }

   public AccountYear getAccountYear()
   {
      return this.accountYear;
   }

   public void setAccountYear(final AccountYear accountYear)
   {
      this.accountYear = accountYear;
   }

}