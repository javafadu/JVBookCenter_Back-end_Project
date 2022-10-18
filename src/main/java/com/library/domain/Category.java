package com.library.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "tbl_categories")
@Entity
@JsonIgnoreProperties(value= {"handler","hibernateLazyInitializer","FieldHandler"})
public class Category {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @Column(nullable = false, length = 80)
    private String name;


    @Column(nullable = false)
    private Boolean builtIn=false;


    @Column(nullable = false)
    private Integer sequence;

    @JsonIgnore
    @OneToMany(mappedBy = "bookCategory")
    private List<Book> categoryBooks=new ArrayList<>();

}
