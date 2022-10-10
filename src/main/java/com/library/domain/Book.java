package com.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, nullable=false)
    private String name;

    @Column(length = 17, nullable=false)
    private String isbn;

    @Column(nullable=true)
    private Integer pageCount;

    @Column(nullable=false)
    private Long authorId;

    @Column(nullable=false)
    private Long publisherId;

    @Column(nullable=true)
    private Integer publishDate;

    @Column(nullable=false)
    private Long categoryId;

    @Column(nullable=true)
    private byte[] image;

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
