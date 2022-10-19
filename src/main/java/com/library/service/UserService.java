package com.library.service;

import com.library.domain.Role;
import com.library.domain.User;
import com.library.domain.enums.RoleType;
import com.library.dto.UserDTO;
import com.library.dto.mapper.UserMapper;
import com.library.dto.request.RegisterRequest;

import com.library.dto.response.UserRegisterResponse;
import com.library.dto.response.UserResponse;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    // 1- ADD ROLE SERVICE
    public List<String> addRoles() {
        // create a blank list to be added already exist roles in DB
        List<String> existRolesList= new ArrayList<>();

        for (RoleType each: RoleType.values()
        ) {
            if(!roleRepository.existsByName(each)) {
                Role role = new Role();
                role.setName(each);
                roleRepository.save(role);
            } else {
                existRolesList.add(each.name());
            }
        }

        return existRolesList;
    }



    public UserRegisterResponse register(RegisterRequest registerRequest){
        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new RuntimeException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }

        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() -> new RuntimeException(
                String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE,RoleType.ROLE_MEMBER.name())));

        Set<Role> roles = new HashSet<>();

        roles.add(role);



        LocalDateTime today = LocalDateTime.now();

        User user = new User();

        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setCreateDate(today);
        user.setScore(0);

        userRepository.save(user);

        return userMapper.userToUserRegisterResponse(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));
    }

    public List<UserResponse> getAllUsers(){
        List<User> users= userRepository.findAll();
        return userMapper.map(users);
    }

    public Page<UserResponse> getUserPage(Pageable pageable){
        Page<UserResponse> users= userRepository.findAllWithPage(pageable);

        return users;
    }

}
