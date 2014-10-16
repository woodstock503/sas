package sas.saccplus.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;

import javax.persistence.ManyToOne;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import sas.saccplus.model.Company;

@Entity
public class Department implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -7639285871264369297L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String departmentName;

   @Column
   private String departmentNameEn;

   @Column
   private boolean departmentStatus;

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
         return id.equals(((Department) that).id);
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

   public String getDepartmentName()
   {
      return this.departmentName;
   }

   public void setDepartmentName(final String departmentName)
   {
      this.departmentName = departmentName;
   }

   public String getDepartmentNameEn()
   {
      return this.departmentNameEn;
   }

   public void setDepartmentNameEn(final String departmentNameEn)
   {
      this.departmentNameEn = departmentNameEn;
   }

   public boolean getDepartmentStatus()
   {
      return this.departmentStatus;
   }

   public void setDepartmentStatus(final boolean departmentStatus)
   {
      this.departmentStatus = departmentStatus;
   }

   public Company getCompany()
   {
      return this.company;
   }

   public void setCompany(final Company company)
   {
      this.company = company;
   }

   public String getCreateBy()
   {
      return this.createBy;
   }

   public void setCreateBy(final String createBy)
   {
      this.createBy = createBy;
   }

   public Date getCreateDateTime()
   {
      return this.createDateTime;
   }

   public void setCreateDateTime(final Date createDateTime)
   {
      this.createDateTime = createDateTime;
   }

   public String getUpdateBy()
   {
      return this.updateBy;
   }

   public void setUpdateBy(final String updateBy)
   {
      this.updateBy = updateBy;
   }

   public Date getUpdateDateTime()
   {
      return this.updateDateTime;
   }

   public void setUpdateDateTime(final Date updateDateTime)
   {
      this.updateDateTime = updateDateTime;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (departmentName != null && !departmentName.trim().isEmpty())
         result += "departmentName: " + departmentName;
      if (departmentNameEn != null && !departmentNameEn.trim().isEmpty())
         result += ", departmentNameEn: " + departmentNameEn;
      result += ", departmentStatus: " + departmentStatus;
      if (createBy != null && !createBy.trim().isEmpty())
         result += ", createBy: " + createBy;
      if (updateBy != null && !updateBy.trim().isEmpty())
         result += ", updateBy: " + updateBy;
      return result;
   }
}