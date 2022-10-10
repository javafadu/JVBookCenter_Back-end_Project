package com.library.domain;

import com.library.domain.enums.RoleType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="tbl_roles")

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType name;

<<<<<<< HEAD
    public Role(RoleType name) {
        this.name = name;
    }
=======
>>>>>>> cd73304 (v2)
}
