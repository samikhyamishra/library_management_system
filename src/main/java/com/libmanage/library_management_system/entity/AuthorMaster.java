package com.libmanage.library_management_system.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.awt.print.Book;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "author_master")
public class AuthorMaster {

//    @Serial
//    private static final long serialVersionUID = 1L;
    @SequenceGenerator(name = "author_master_sequence", sequenceName = "author_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "author_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_master_sequence")
    private Integer authorId;

    @Column(name = "first_name", length = 25, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 25, nullable = false)
    private String lastName;

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

//    @ManyToMany(mappedBy = "authors", cascade = CascadeType.ALL)
//    private Set<BookMaster> books = new HashSet<BookMaster>();

//    public AuthorMaster(Long authorId, String firstName, String lastName, Date createdOn, Long createdBy, Date updatedOn, Long updatedBy) {
//        this.authorId = authorId;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.createdOn = createdOn;
//        this.createdBy = createdBy;
//        this.updatedOn = updatedOn;
//        this.updatedBy = updatedBy;
//    }
//
//    public void removeBooks(BookMaster book){
//        this.books.remove(book);
//        book.getAuthors().remove(book);
//    }
//
//    public void addBooks(BookMaster book) {
//        this.books.add(book);
//        book.getAuthors().add(this);
//    }
}
