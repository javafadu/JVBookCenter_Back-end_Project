package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_books")
@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 17, nullable = false)
    private String isbn;

    @Column
    private Integer pageCount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "authorId", nullable = false)
    private Author bookAuthor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "publisherId", nullable = false)
    private Publisher bookPublisher;

    @Column
    private Integer publishDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoryId", nullable = false)
    private Category bookCategory;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name="book_id")
    private Set<ImageFile> image;


    @Column(nullable = false)
    private Boolean loanable = true;

    @Column(length = 6, nullable = false)
    private String shelfCode;

    @Column(nullable = false)
    private Boolean active = true;

    @Column(nullable = false)
    private Boolean featured = false;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Column(nullable = false)
    private Boolean builtIn = false;

    @JsonIgnore
    @OneToMany(mappedBy = "loanedBooks")
    private List<Loan> loanedBooks = new ArrayList<>();


}
