package com.library.domain;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_authors")
public class Authors {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Size(min =4,max = 70)
    private String name;

    @Column(nullable = false)
    private Boolean builtIn=false;
}
