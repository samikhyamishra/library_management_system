package com.libmanage.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.apache.catalina.User;
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
@Table(name = "borrowed_book_master")
public class BorrowedBookMaster{

//    @Serial
//    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "borrow_master_sequence", sequenceName = "borrow_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "borrow_book_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "borrow_master_sequence")
    private Integer BorrowBookId;

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Column(name = "genre_id", nullable = false)
    private Integer genreId;

    @Column(name = "issue_date", nullable = false)
    private Date issueDate;

    @Column(name = "due_date", nullable = false)
    private Date dueDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "is_active")
    private Boolean isActive;


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

////    borrowed book and inventory 1-1 mapping
//@OneToOne(cascade = CascadeType.ALL)
//@JoinColumn(name = "invent_id")
//private BookInventoryMaster inventory;
////@JoinTable(name = "borrowed_inventory",
////        joinColumns = {@JoinColumn(name = "book_id")},
////        inverseJoinColumns = {@JoinColumn(name = "is_available")})
////private Set<BookInventoryMaster> inventory = new HashSet<BookInventoryMaster>();
//
////  borrowed and user m-1 mapping
//@OneToMany(mappedBy = "borrowed", cascade = CascadeType.ALL)
//private Set<UsersMaster> users = new HashSet<UsersMaster>();
//
////    to ensure bi-directionally we are adding and removing the entities which are mapped to each other to avoid errors
//    public void removeInventory(BookInventoryMaster inventory){
////            this.inventory.remove(inventory);
////            inventory.getBorrowed().remove(inventory);
//        inventory.setBorrowed(null);
//        }
//
//        public void addInventory(BookInventoryMaster inventory){
////            this.inventory.add(inventory);
////            inventory.getBorrowed().add(this);
//            inventory.setBorrowed(this);
//        }
//
//        public void removeUsers(UsersMaster user){
//        this.users.remove(user);
//        user.getBorrowed().remove(user);
//    }
//
//    public void addUsers(UsersMaster user){
//        this.users.add(user);
//        user.getBorrowed().add(this);
//    }


}
