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
@Table(name = "menu_master")
public class MenuMaster{

//    @Serial
//    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "menu_master_sequence", sequenceName = "menu_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "menu_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "menu_master_sequence")
    private Integer menuId;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "parent_id", nullable = false)
    private Integer parentId;

    @Column(name = "role_id")
    private Integer roleId;

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

    @Column(name = "update_by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer updateBy;

////    permission and menu 1-1 mapping
//    @OneToOne(mappedBy = "menu", cascade = CascadeType.ALL)
//    private RoleMenuPermission permission;
//
////    bidirectional mapping
//    public void removePermission(RoleMenuPermission permissions){
////        this.permission.remove(permissions);
////        permissions.getMenu().remove(permissions);
//        permissions.setMenu(null);
//    }
//
//    public void addPermission(RoleMenuPermission permissions){
////        this.permission.add(permissions);
////        permissions.getMenu().add(this);
//        permissions.setMenu(this);
//    }
//
//    public MenuMaster(String menuName, Date createdOn, Long createdBy, Date updatedOn, Long updateBy) {
//        this.menuName = menuName;
//        this.createdOn = createdOn;
//        this.createdBy = createdBy;
//        this.updatedOn = updatedOn;
//        this.updateBy = updateBy;
//    }
}
