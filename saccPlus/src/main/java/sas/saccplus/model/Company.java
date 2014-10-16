package sas.saccplus.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
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

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;
import javax.persistence.Lob;

import sas.saccplus.model.Currency;
import sas.saccplus.model.Period;

@Entity
public class Company implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -322584556297666519L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String companyName;

   @Column
   private String companyNameEn;

   @Column
   private String createBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createDateTime;

   @Column
   private String updateBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDateTime;

   @Column
   private boolean companyStatus;

   @ManyToOne
   private Currency currency;

   @Column
   private String digitPattern;

   @Temporal(TemporalType.TIMESTAMP)
   private Date startDate;

   @Temporal(TemporalType.TIMESTAMP)
   private Date endDate;

   @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<Period> periods = new HashSet<Period>();

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

   @Temporal(TemporalType.TIMESTAMP)
   private Date establishDateTime;

   @Column
   private int period;

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
         return id.equals(((Company) that).id);
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

   public String getCompanyName()
   {
      return this.companyName;
   }

   public void setCompanyName(final String companyName)
   {
      this.companyName = companyName;
   }

   public String getCompanyNameEn()
   {
      return this.companyNameEn;
   }

   public void setCompanyNameEn(final String companyNameEn)
   {
      this.companyNameEn = companyNameEn;
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

   public boolean getCompanyStatus()
   {
      return this.companyStatus;
   }

   public void setCompanyStatus(final boolean companyStatus)
   {
      this.companyStatus = companyStatus;
   }

   public Currency getCurrency()
   {
      return this.currency;
   }

   public void setCurrency(final Currency currency)
   {
      this.currency = currency;
   }

   public String getDigitPattern()
   {
      return this.digitPattern;
   }

   public void setDigitPattern(final String digitPattern)
   {
      this.digitPattern = digitPattern;
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

   public Set<Period> getPeriods()
   {
      return this.periods;
   }

   public void setPeriods(final Set<Period> periods)
   {
      this.periods = periods;
   }

   public void newCurrency()
   {
      this.currency = new Currency();
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

   public Date getEstablishDateTime()
   {
      return this.establishDateTime;
   }

   public void setEstablishDateTime(final Date establishDateTime)
   {
      this.establishDateTime = establishDateTime;
   }

   public int getPeriod()
   {
      return this.period;
   }

   public void setPeriod(final int period)
   {
      this.period = period;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "serialVersionUID: " + serialVersionUID;
      if (companyName != null && !companyName.trim().isEmpty())
         result += ", companyName: " + companyName;
      if (companyNameEn != null && !companyNameEn.trim().isEmpty())
         result += ", companyNameEn: " + companyNameEn;
      if (createBy != null && !createBy.trim().isEmpty())
         result += ", createBy: " + createBy;
      if (updateBy != null && !updateBy.trim().isEmpty())
         result += ", updateBy: " + updateBy;
      result += ", companyStatus: " + companyStatus;
      if (digitPattern != null && !digitPattern.trim().isEmpty())
         result += ", digitPattern: " + digitPattern;
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
      result += ", period: " + period;
      return result;
   }

}