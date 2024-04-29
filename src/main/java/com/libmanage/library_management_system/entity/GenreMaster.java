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
//@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "genre_master")
public class GenreMaster{

//    @Serial
//    private static final long serialVersionUID = 1L;

    @SequenceGenerator(name = "genre_master_sequence", sequenceName = "genre_m_id_seq", allocationSize = 1)
    @Id
    @Column(name = "genre_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "genre_master_sequence")
    private Integer genreId;

    @Column(name = "genre_name", nullable = false)
    private String genreName;

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

//    @ManyToMany(mappedBy = "genre", cascade = CascadeType.ALL)
//    private Set<BookMaster> books = new HashSet<BookMaster>();

//    public GenreMaster(Long genreId, String genreName, Date createdOn, Long createdBy, Date updatedOn, Long updatedBy) {
//        this.genreId = genreId;
//        this.genreName = genreName;
//        this.createdOn = createdOn;
//        this.createdBy = createdBy;
//        this.updatedOn = updatedOn;
//        this.updatedBy = updatedBy;
//    }
//
//    public void removeBooks(BookMaster book){
//        this.books.remove(book);
//        book.getGenre().remove(book);
//    }
//    public void addBooks(BookMaster book){
//        this.books.add(book);
//        book.getGenre().add(this);
//    }

}
