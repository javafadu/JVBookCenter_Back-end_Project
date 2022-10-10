package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="tbl_books")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable=false)
    private String name;

    @Column(length = 17, nullable=false)
    private String isbn;

    @Column
    private Integer pageCount;

    @Column(nullable=false)
    private Long authorId;

    @Column(nullable=false)
    private Long publisherId;

    @Column
    private Integer publishDate;

    @Column(nullable=false)
    private Long categoryId;

    @Column
    private File image;

    @Column(nullable=false)
    private Boolean loanable = true;

    @Column(length = 6, nullable=false)
    private String shelfCode;

    @Column(nullable=false)
    private Boolean active = true;

    @Column(nullable=false)
    private Boolean featured = false;

    @Column(nullable=false)
    private LocalDateTime createDate;

    @Column(nullable=false)
    private Boolean builtIn =false;


}
