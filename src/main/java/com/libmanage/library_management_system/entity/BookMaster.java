package com.libmanage.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.id.IntegralDataTypeHolder;

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
@Table(name = "book_master")
public class BookMaster {

//    @Serial
//    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "book_master_sequence", sequenceName = "book_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "book_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "book_master_sequence")
    private Integer bookId;

    @Column(name = "title")
    private String title;

    @Column(name = "author_id", nullable = false)
    private Integer authorId;

    @Column(name = "genre_id", nullable = false)
    private Integer genreId;

    @Column(name = "is_active", insertable=false, updatable=false )
    private Boolean active;

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

////    book and author table m-m mapping
//
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(name = "books_authors",
//               joinColumns = {@JoinColumn(name="book_id")},
//               inverseJoinColumns = {@JoinColumn(name = "author_id")})
//    private Set<AuthorMaster> authors = new HashSet<AuthorMaster>();
//
////    book and genre table m-1 mapping
//
//    @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(name = "books_genre",
//                 joinColumns = {@JoinColumn(name = "book_id")},
//                 inverseJoinColumns = {@JoinColumn(name = "genre_id")})
//    private Set<GenreMaster> genre = new HashSet<GenreMaster>();
//
////    book and book inventory table 1-1 mapping
//    @OneToOne(cascade = CascadeType.ALL)
//  @JoinColumn(name = "book_id")
//    private BookInventoryMaster inventory;
//
//
////    @JoinTable(name = "book_inventory",
////               joinColumns = {@JoinColumn(name = "is_available")},
////               inverseJoinColumns = {@JoinColumn(name = "is_available")})
////    private Set<BookInventoryMaster> inventory = new HashSet<BookInventoryMaster>();
//
//    //    to ensure bi-directionally we are adding and removing the entities which are mapped to each other to avoid errors
//    public void removeInventory(BookInventoryMaster inventory){
////        this.inventory.remove(inventory);
////        inventory.getBooks().remove(inventory);
//        inventory.setBooks(null);
//    }
//
//    public void addInventory(BookInventoryMaster inventory){
////        this.inventory.add(inventory);
////        inventory.getBooks().add(this);
//        inventory.setBooks(this);
//    }
//
//    public void removeGenre(GenreMaster genre){
//        this.genre.remove(genre);
//        genre.getBooks().remove(genre);
//    }
//
//    public void addGenre(GenreMaster genre){
//        this.genre.add(genre);
//        genre.getBooks().add(this);
//    }
//
//    public void removeAuthor(AuthorMaster author){
//        this.authors.remove(author);
//        author.getBooks().remove(author);
//    }
//
//    public void addAuthor(AuthorMaster author){
//        this.authors.add(author);
//        author.getBooks().add(this);
//    }
}
