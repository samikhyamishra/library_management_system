package com.libmanage.library_management_system.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * $table.getTableComment()
 */
//@Setter
//@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "role_menu_permission")
public class RoleMenuPermission{
    @Id
    @Column(name = "rmp_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rmpId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "menu_id", nullable = false, insertable=false, updatable=false)
    private Long menuId;

    @Column(name = "is_accessible")
    private Boolean accessible;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_on")
    private Date updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;

////    role and permission 1-1 mapping
//    @OneToOne(mappedBy = "permission",cascade = CascadeType.ALL)
//    private RoleMaster role;
//
////    permission and menu 1-1 mapping
//    @OneToOne(cascade = CascadeType.ALL)
////    @JoinTable(name = "permission_menu",
////               joinColumns = {@JoinColumn(name = "menu_id")},
////            inverseJoinColumns = {@JoinColumn(name = "menu_id")} )
//    @JoinColumn(name = "menu_id")
//    private MenuMaster menu;
//
////    bidirectional mapping
//    public void removeRole(RoleMaster roles){
////        this.role.remove(roles);
////        roles.getPermission().remove(roles);
//        roles.setPermission(null);
//    }
//
//    public void addRole(RoleMaster roles){
////        this.role.add(roles);
////        roles.getPermission().add(this);
//        roles.setPermission(this);
//    }
//
//    public void removeMenu(MenuMaster menu){
////        this.menu.remove(menu);
////        menu.getPermission().remove(menu);
//        menu.setPermission(null);
//    }
//
//    public void addMenu(MenuMaster menu){
////        this.menu.add(menu);
////        menu.getPermission().add(this);
//        menu.setPermission(this);
//    }
}
