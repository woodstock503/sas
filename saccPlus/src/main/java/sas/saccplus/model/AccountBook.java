package sas.saccplus.model;

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

@Entity
public class AccountBook implements Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = 6654738750074746424L;
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @Column
   private String accountBookName;

   @Column
   private String accountBookNameEn;

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

   @Column
   private boolean accountBookStatus;

   @Column
   private String accountBookCode;

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
         return id.equals(((AccountBook) that).id);
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

   public String getAccountBookName()
   {
      return this.accountBookName;
   }

   public void setAccountBookName(final String accountBookName)
   {
      this.accountBookName = accountBookName;
   }

   public String getAccountBookNameEn()
   {
      return this.accountBookNameEn;
   }

   public void setAccountBookNameEn(final String accountBookNameEn)
   {
      this.accountBookNameEn = accountBookNameEn;
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

   public boolean getAccountBookStatus()
   {
      return this.accountBookStatus;
   }

   public void setAccountBookStatus(final boolean accountBookStatus)
   {
      this.accountBookStatus = accountBookStatus;
   }

   public String getAccountBookCode()
   {
      return this.accountBookCode;
   }

   public void setAccountBookCode(final String accountBookCode)
   {
      this.accountBookCode = accountBookCode;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (accountBookName != null && !accountBookName.trim().isEmpty())
         result += "accountBookName: " + accountBookName;
      if (accountBookNameEn != null && !accountBookNameEn.trim().isEmpty())
         result += ", accountBookNameEn: " + accountBookNameEn;
      if (description != null && !description.trim().isEmpty())
         result += ", description: " + description;
      if (createBy != null && !createBy.trim().isEmpty())
         result += ", createBy: " + createBy;
      if (updateBy != null && !updateBy.trim().isEmpty())
         result += ", updateBy: " + updateBy;
      result += ", accountBookStatus: " + accountBookStatus;
      if (accountBookCode != null && !accountBookCode.trim().isEmpty())
         result += ", accountBookCode: " + accountBookCode;
      return result;
   }
}