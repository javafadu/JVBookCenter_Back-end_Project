package com.library.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "tbl_categories")
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @Column(nullable = false, length = 80)
    private String name;


    @Column(nullable = false)
    private Boolean builtin=false;



    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer sequence;

}
