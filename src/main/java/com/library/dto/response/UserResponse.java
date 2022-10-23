package com.library.dto.response;


import com.library.domain.Role;
import com.library.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private Set<Role> roles;

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
        this.roles=user.getRoles();
    }
}
