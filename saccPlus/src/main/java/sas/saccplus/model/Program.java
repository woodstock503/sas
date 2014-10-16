package sas.saccplus.model;

import javax.persistence.Entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;

import java.lang.Override;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import java.util.Set;
import java.util.HashSet;

import javax.persistence.OneToMany;

import sas.saccplus.model.ProgramRole;

@Entity
public class Program implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -7975982065694884008L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String programName;

   @Column
   private String programNameEn;

   @Column
   private boolean programStatus;

   @Column
   private String url;

   @Column
   private String description;

   @Column
   private String createBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createDateTime;

   @Column
   private String updateBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDateTime;

   @OneToMany(mappedBy = "program", cascade = CascadeType.ALL, orphanRemoval = true)
   private Set<ProgramRole> programRoles = new HashSet<ProgramRole>();

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
         return id.equals(((Program) that).id);
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

   public String getProgramName()
   {
      return this.programName;
   }

   public void setProgramName(final String programName)
   {
      this.programName = programName;
   }

   public String getProgramNameEn()
   {
      return this.programNameEn;
   }

   public void setProgramNameEn(final String programNameEn)
   {
      this.programNameEn = programNameEn;
   }

   public boolean getProgramStatus()
   {
      return this.programStatus;
   }

   public void setProgramStatus(final boolean programStatus)
   {
      this.programStatus = programStatus;
   }

   public String getUrl()
   {
      return this.url;
   }

   public void setUrl(final String url)
   {
      this.url = url;
   }

   public String getDescription()
   {
      return this.description;
   }

   public void setDescription(final String description)
   {
      this.description = description;
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
      if (programName != null && !programName.trim().isEmpty())
         result += "programName: " + programName;
      if (programNameEn != null && !programNameEn.trim().isEmpty())
         result += ", programNameEn: " + programNameEn;
      result += ", programStatus: " + programStatus;
      if (url != null && !url.trim().isEmpty())
         result += ", url: " + url;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (createBy != null && !createBy.trim().isEmpty())
         result += ", createBy: " + createBy;
      if (updateBy != null && !updateBy.trim().isEmpty())
         result += ", updateBy: " + updateBy;
      return result;
   }

   public Set<ProgramRole> getProgramRoles()
   {
      return this.programRoles;
   }

   public void setProgramRoles(final Set<ProgramRole> programRoles)
   {
      this.programRoles = programRoles;
   }

}