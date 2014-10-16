package sas.saccplus.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import java.lang.Override;
import java.util.Date;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Role implements java.io.Serializable
{

   /**
    * 
    */
   private static final long serialVersionUID = -5294721658387639412L;
   @Id
   private @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "id", updatable = false, nullable = false)
   Long id = null;
   @Version
   private @Column(name = "version")
   int version = 0;

   @Column
   private String roleName;

   @Column
   private String roleNameEn;

   @Column
   private String createBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date createDateTime;

   @Column
   private String updateBy;

   @Temporal(TemporalType.TIMESTAMP)
   private Date updateDateTime;

   @Column
   private boolean roleStatus;

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
         return id.equals(((Role) that).id);
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

   //   @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = false)
   //   private Set<MenuRole> menuRole = new HashSet<MenuRole>();
   //
   //   public Set<MenuRole> getMenuRole()
   //   {
   //      return menuRole;
   //   }
   //
   //   public void setMenuRole(Set<MenuRole> menuRole)
   //   {
   //      this.menuRole = menuRole;
   //   }

   //   @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = false)
   //   private Set<UserRole> userRole = new HashSet<UserRole>();
   //
   //   public Set<UserRole> getUserRole()
   //   {
   //      return userRole;
   //   }
   //
   //   public void setUserRole(Set<UserRole> userRole)
   //   {
   //      this.userRole = userRole;
   //   }

   public String getRoleName()
   {
      return roleName;
   }

   public void setRoleName(String roleName)
   {
      this.roleName = roleName;
   }

   public String getRoleNameEn()
   {
      return roleNameEn;
   }

   public void setRoleNameEn(String roleNameEn)
   {
      this.roleNameEn = roleNameEn;
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

   public boolean getRoleStatus()
   {
      return this.roleStatus;
   }

   public void setRoleStatus(final boolean roleStatus)
   {
      this.roleStatus = roleStatus;
   }

   @Override
   public String toString()
   {
      String result = getClass().getSimpleName() + " ";
      result += "serialVersionUID: " + serialVersionUID;
      if (roleName != null && !roleName.trim().isEmpty())
         result += ", roleName: " + roleName;
      if (roleNameEn != null && !roleNameEn.trim().isEmpty())
         result += ", roleNameEn: " + roleNameEn;
      if (createBy != null && !createBy.trim().isEmpty())
         result += ", createBy: " + createBy;
      if (updateBy != null && !updateBy.trim().isEmpty())
         result += ", updateBy: " + updateBy;
      result += ", roleStatus: " + roleStatus;
      return result;
   }

}