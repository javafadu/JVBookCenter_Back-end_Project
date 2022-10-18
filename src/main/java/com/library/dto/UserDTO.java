package com.library.dto;

import com.library.domain.Loan;
import com.library.domain.Role;
import com.library.domain.enums.RoleType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer score=0;

    private String address;

    private String phone;

    private Date birthDate;

    private String email;

    private String password;

    private LocalDateTime createDate;

    private String resetPasswordCode;

    private Boolean builtIn=false;

    private List<Loan> userBooks=new ArrayList<>();

    private Set<String> roles;


    // Converting Set<Role> roles in DB to Set<String> roles as dto
    public void setRoles(Set<Role> roles) {
        Set<String> rolesStr = new HashSet<>();

        for (Role r:roles
        ) {
            if(r.getName().equals(RoleType.ROLE_MEMBER)) {
                rolesStr.add("Member");
            } else if (r.getName().equals(RoleType.ROLE_STAFF)) {
                rolesStr.add("Staff");
            } else if (r.getName().equals(RoleType.ROLE_ADMIN)) {
                rolesStr.add("Administrator");
            } else {
                rolesStr.add("Anonymous");
            }
        }

        this.roles=rolesStr;

    }


}
