package com.libmanage.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jetbrains.annotations.NotNull;

import java.awt.print.Book;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
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
@Table(name = "book_inventory_master")
public class BookInventoryMaster{

//    @Serial
//    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "invent_master_sequence", sequenceName = "invent_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "invent_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "invent_master_sequence")
    private Integer inventId;

    @Column(name = "book_id", nullable = false)
    private Integer bookId;

    @Column(name = "no_of_copies")
    private Integer noOfCopies;

//    @Column(name = "is_available", nullable = false, insertable=false, updatable=false)
//    private Boolean available;

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

//    @OneToOne(mappedBy = "inventory", cascade = CascadeType.ALL)
//    private BookMaster books;
//
//    @OneToOne(mappedBy = "inventory",cascade = CascadeType.ALL)
//    private BorrowedBookMaster borrowed;

//    public void removeBooks(@NotNull BookMaster book){
////        this.books.remove(book);
////        book.getInventory().remove(book);
//        book.setInventory(null);
//    }
//
//    public void addBooks(@NotNull BookMaster book){
////        this.books.add(book);
////        book.getInventory().add(this);
//        book.setInventory(this);
//    }
//
//    public void removeBorrowed(@NotNull BorrowedBookMaster borrow){
////        this.borrowed.remove(borrow);
////        borrow.getInventory().remove(borrow);
//        borrow.setInventory(null);
//    }
//
//    public void addBorrowed(@NotNull BorrowedBookMaster borrow){
////        this.borrowed.add(borrow);
////        borrow.getInventory().add(this);
//        borrow.setInventory(this);
//    }


}
