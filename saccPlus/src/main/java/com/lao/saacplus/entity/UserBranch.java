package com.lao.saacplus.entity;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Version;
import java.lang.Override;
import com.lao.saacplus.entity.UserProfile;
import javax.persistence.ManyToOne;
import com.lao.saacplus.entity.Branch;

@Entity
public class UserBranch implements Serializable
{

   /**
	 * 
	 */
	private static final long serialVersionUID = -5231727616535012788L;
@Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   private Long id = null;
   @Version
   @Column(name = "version")
   private int version = 0;

   @ManyToOne
   private UserProfile userProfile;

   @ManyToOne
   private Branch branch;

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
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      if (id != null)
         result += "id: " + id;
      return result;
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
         return id.equals(((UserBranch) that).id);
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

   public UserProfile getUserProfile()
   {
      return this.userProfile;
   }

   public void setUserProfile(final UserProfile userProfile)
   {
      this.userProfile = userProfile;
   }

   public Branch getBranch()
   {
      return this.branch;
   }

   public void setBranch(final Branch branch)
   {
      this.branch = branch;
   }
}