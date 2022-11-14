package com.library.dto.response;


import com.library.domain.Role;
import com.library.domain.User;
import com.library.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    private Long id;

    private String firstName;

    private String lastName;

    private Integer score;

    private String address;

    private String phone;

    private LocalDate birthDate;

    private String email;


    private LocalDateTime createDate;

    private Boolean builtIn;

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


    public UserResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.birthDate = user.getBirthDate();
        this.email = user.getEmail();
        this.score = user.getScore();
        this.builtIn = user.getBuiltIn();
        this.createDate=user.getCreateDate();
        this.roles=getRoles();  // bu sekilde yapinca calisiyor. Set<Role> leri Set<String> e
        // yukardaki methodan dan donusturuyor.
    }
}
