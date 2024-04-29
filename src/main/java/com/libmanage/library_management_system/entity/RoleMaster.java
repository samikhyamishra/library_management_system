package com.libmanage.library_management_system.entity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * $table.getTableComment()
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "role_master")
public class RoleMaster{

//    @Serial
//    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "role_master_sequence", sequenceName = "role_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "role_id", nullable = false, insertable=false, updatable=false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "role_master_sequence")
    private Integer roleId;

//    @Column(name = "user_name")
//    private String userName;

    @Column(name = "role_name")
    private String roleName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    @CreationTimestamp
    private Date createdOn;

    @Column(name = "created_by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn;

    @Column(name = "updated_by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer updatedBy;

    //user and role 1-1 mapping
//    @OneToOne(mappedBy = "role",cascade = CascadeType.ALL)
//    private UsersMaster users;
//
////    role and role menu permission
//     @OneToOne(cascade = CascadeType.ALL)
//     @JoinColumn(name = "is_accessible")
////     @JoinTable(name = "role_permission",
////               joinColumns = {@JoinColumn(name = "role_id")},
////               inverseJoinColumns = {@JoinColumn(name = "is_accessible")})
//      private RoleMenuPermission permission;
//
////     bi-directional mapping
//    public void removeUsers(UsersMaster user){
////        this.users.remove(user);
////        user.getRole().remove(user);
//         user.setRole(null);
//    }
//
//    public void addUsers(UsersMaster user){
////        this.users.add(user);
////        user.getRole().add(this);
//        user.setRole(this);
//    }
//
//    public void removePermission(RoleMenuPermission permissions){
////        this.permission.remove(permissions);
////        permissions.getRole().remove(permissions);
//        permissions.setRole(null);
//    }
//
//    public void addPermission(RoleMenuPermission permissions){
////        this.permission.add(permissions);
////        permissions.getRole().add(this);
//        permissions.setRole(this);
//    }
//
//    public RoleMaster(String userName, Date createdOn, Long createdBy, Date updatedOn, Long updatedBy) {
//        this.userName = userName;
//        this.createdOn = createdOn;
//        this.createdBy = createdBy;
//        this.updatedOn = updatedOn;
//        this.updatedBy = updatedBy;
//    }
}
