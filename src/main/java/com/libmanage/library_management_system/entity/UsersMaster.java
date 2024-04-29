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
@Table(name = "users_master")
public class UsersMaster {

//    @Serial
//    private static final long serialVersionUID = 1L;
     @Id
     @SequenceGenerator(name = "user_master_sequence", sequenceName = "user_m_id_seq", allocationSize = 1)
     @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "user_master_sequence")
     @Column(name = "user_login_id")
     private Integer userLoginId;

    @Column(name = "user_name", nullable = false)
    private String userName;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

//    @Column(name = "middle_name")
//    private String middleName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone_no", nullable = false)
    private String phoneNo;

    @Column(name = "address")
    private String address;

    @Column(name = "role_id", nullable = false, insertable=false, updatable=false)
    private Integer roleId;

    @Column(name = "is_active")
    private Boolean active;

    @Column(name = "token")
    private String token;

    @Column(name = "created_by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    @CreationTimestamp
    private Date createdOn = new Date(System.currentTimeMillis());

    @Column(name = "updated_by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    @UpdateTimestamp
    private Date updatedOn=new Date(System.currentTimeMillis());


////    user and borrowed 1-m mapping
//@OneToMany(cascade = CascadeType.ALL)
//@JoinTable(name = "user_inventory",
//        joinColumns = {@JoinColumn(name = "user_login_id")},
//        inverseJoinColumns = {@JoinColumn(name = "book_id")})
//private Set<BorrowedBookMaster> borrowed = new HashSet<BorrowedBookMaster>();
//
////user and role 1-1 mapping
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "role_id")
////    @JoinTable(name = "user_role",
////              joinColumns = {@JoinColumn(name = "user_login_id")},
////              inverseJoinColumns = {@JoinColumn(name = "role_id")})
//    private RoleMaster role;
//
//    //    to ensure bi-directionally we are adding and removing the entities which are mapped to each other to avoid errors
//
//    public void removeBorrowed(BorrowedBookMaster borrow){
//        this.borrowed.remove(borrow);
//        borrow.getUsers().remove(borrow);
//    }
//
//    public void addBorrowed(BorrowedBookMaster borrow){
//        this.borrowed.add(borrow);
//        borrow.getUsers().remove(this);
//    }
//
//    public void removeRole(RoleMaster roles){
////        this.role.remove(roles);
////        roles.getUsers().remove(roles);
//          roles.setUsers(null);
//    }
//    public void addRole(RoleMaster roles){
////        this.role.add(roles);
////        roles.getUsers().add(this);
//          roles.setUsers(this);
//    }
//
//    public UsersMaster(String password, String userName, Long roleId, String firstName, String middleName, String lastName, String email, String phoneNo, String address, Boolean active, Date createdOn, Long createdBy, Date updatedOn, Long updateBy) {
//        this.password = password;
//        this.userName = userName;
//        this.roleId = roleId;
//        this.firstName = firstName;
//        this.middleName = middleName;
//        this.lastName = lastName;
//        this.email = email;
//        this.phoneNo = phoneNo;
//        this.address = address;
//        this.active = active;
//        this.createdOn = createdOn;
//        this.createdBy = createdBy;
//        this.updatedOn = updatedOn;
//        this.updateBy = updateBy;
//    }
}
