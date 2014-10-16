package com.lao.saacplus.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Lob;
import com.lao.saacplus.entity.Company;
import javax.persistence.ManyToOne;

@Entity
public class Branch implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -2349793997768164211L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String branchName;

   @Column
   private String branchNameEn;

   @Column
   private boolean branchStatus;

   @Column
   private String createBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createDateTime;

   @Column
   private String updateBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDateTime;

   @Column
   private String address;

   @Column
   private String telephone;

   @Column
   private String fax;

   @Column
   private String email;

   @Column
   private String vision;

   @Column
   private String mission;

   @Lob
   @Column(length = 2147483647)
   private byte[] logo;

   @Column
   private String taxId;

   @ManyToOne
   private Company company;

   @Temporal(TemporalType.TIMESTAMP)
   private Date establishDateTime;

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
         return id.equals(((Branch) that).id);
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

   public String getBranchName()
   {
      return this.branchName;
   }

   public void setBranchName(final String branchName)
   {
      this.branchName = branchName;
   }

   public String getBranchNameEn()
   {
      return this.branchNameEn;
   }

   public void setBranchNameEn(final String branchNameEn)
   {
      this.branchNameEn = branchNameEn;
   }

   public boolean getBranchStatus()
   {
      return this.branchStatus;
   }

   public void setBranchStatus(final boolean branchStatus)
   {
      this.branchStatus = branchStatus;
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

   public String getAddress()
   {
      return this.address;
   }

   public void setAddress(final String address)
   {
      this.address = address;
   }

   public String getTelephone()
   {
      return this.telephone;
   }

   public void setTelephone(final String telephone)
   {
      this.telephone = telephone;
   }

   public String getFax()
   {
      return this.fax;
   }

   public void setFax(final String fax)
   {
      this.fax = fax;
   }

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(final String email)
   {
      this.email = email;
   }

   public String getVision()
   {
      return this.vision;
   }

   public void setVision(final String vision)
   {
      this.vision = vision;
   }

   public String getMission()
   {
      return this.mission;
   }

   public void setMission(final String mission)
   {
      this.mission = mission;
   }

   public byte[] getLogo()
   {
      return this.logo;
   }

   public void setLogo(final byte[] logo)
   {
      this.logo = logo;
   }

   public String getTaxId()
   {
      return this.taxId;
   }

   public void setTaxId(final String taxId)
   {
      this.taxId = taxId;
   }

   public Company getCompany()
   {
      return this.company;
   }

   public void setCompany(final Company company)
   {
      this.company = company;
   }

   public Date getEstablishDateTime()
   {
      return this.establishDateTime;
   }

   public void setEstablishDateTime(final Date establishDateTime)
   {
      this.establishDateTime = establishDateTime;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "serialVersionUID: " + serialVersionUID;
      if (branchName != null && !branchName.trim().isEmpty())
         result += ", branchName: " + branchName;
      if (branchNameEn != null && !branchNameEn.trim().isEmpty())
         result += ", branchNameEn: " + branchNameEn;
      if (createBy != null && !createBy.trim().isEmpty())
         result += ", createBy: " + createBy;
      if (updateBy != null && !updateBy.trim().isEmpty())
         result += ", updateBy: " + updateBy;
      if (address != null && !address.trim().isEmpty())
         result += ", address: " + address;
      if (telephone != null && !telephone.trim().isEmpty())
         result += ", telephone: " + telephone;
      if (fax != null && !fax.trim().isEmpty())
         result += ", fax: " + fax;
      if (email != null && !email.trim().isEmpty())
         result += ", email: " + email;
      if (vision != null && !vision.trim().isEmpty())
         result += ", vision: " + vision;
      if (mission != null && !mission.trim().isEmpty())
         result += ", mission: " + mission;
      if (logo != null)
         result += ", logo: " + logo;
      if (taxId != null && !taxId.trim().isEmpty())
         result += ", taxId: " + taxId;
      return result;
   }
}