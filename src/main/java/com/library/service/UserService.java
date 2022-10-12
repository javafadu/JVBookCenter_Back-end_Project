package com.library.service;

import com.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    public void saveUserService() {



    //    Role role = new Role();
    //    Set<Role> roles = new HashSet<>();
    //    role.setName(RoleType.ROLE_ADMIN);
    //    roles.add(role);

    //    Loan loan = new Loan();
    //    List<Loan> loans = new ArrayList<>();
    //
    //    loan.setNotes("test");
    //    loans.add(loan);


        Date today = Calendar.getInstance().getTime();

        User user = new User();

        user.setFirstName("Ali");
        user.setLastName("Gel");
        user.setScore (0);
        user.setAddress("Adres Adres dfsd");
        user.setPhone("234232342");
        user.setBirthDate(today);
        user.setEmail("test3@test.com");
        user.setPassword("1234a");
        user.setCreateDate(LocalDateTime.now());
        user.setResetPasswordCode("resetpass");
        // user.setBuiltIn(false);
        // user.setUserBooks(loans);
        // user.setRole(roles);

        userRepository.save(user);

    }




}
