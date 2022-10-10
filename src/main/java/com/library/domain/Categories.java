package com.library.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


@Table(name = "tbl_categories")
@Entity
public class Categories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Size(min = 2, max = 80, message = "Your Name '${validatedValue}' must be between {min} and {max} chars long")
    @NotNull(message = "Please provide your name")
    @Column(nullable = false, length = 80)
    private String name;


    @Column(nullable = false)
    private boolean builtin=false;



    @Column(nullable = false)
    private int sequence;

}
