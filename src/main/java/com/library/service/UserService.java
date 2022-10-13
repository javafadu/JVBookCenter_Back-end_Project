package com.library.service;

import com.library.domain.Role;
import com.library.domain.User;
import com.library.domain.enums.RoleType;
import com.library.dto.request.RegisterRequest;

import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;

    private RoleRepository roleRepository;

    public void register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException("Not Found");
        }

        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() -> new RuntimeException("Not Found"));

        Set<Role> roles = new HashSet<>();

        roles.add(role);

        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(user.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword()); //TODO: Encoder yapilacak security kisminda

        userRepository.save(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
    }
}
