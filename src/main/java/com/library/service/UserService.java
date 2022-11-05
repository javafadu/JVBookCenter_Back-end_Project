package com.library.service;


import com.library.domain.Role;
import com.library.domain.User;
import com.library.domain.enums.RoleType;
import com.library.dto.mapper.UserMapper;
import com.library.dto.request.RegisterRequest;
import com.library.dto.request.UpdatePasswordRequest;
import com.library.dto.request.UpdateUserRequest;
import com.library.dto.request.UserCreateRequest;
import com.library.dto.response.UserRegisterResponse;
import com.library.dto.response.UserResponse;
import com.library.exception.BadRequestException;
import com.library.exception.ConflictException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.LoanRepository;
import com.library.repository.RoleRepository;
import com.library.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;


@AllArgsConstructor
@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;
    private LoanRepository loanRepository;

    // 1- ADD ROLE SERVICE
    public List<String> addRoles() {
        // create a blank list to be added already exist roles in DB
        List<String> existRolesList = new ArrayList<>();

        for (RoleType each : RoleType.values()
        ) {
            if (!roleRepository.existsByName(each)) {
                Role role = new Role();
                role.setName(each);
                roleRepository.save(role);
            } else {
                existRolesList.add(each.name());
            }
        }

        return existRolesList;
    }


    // 1- REGISTER a USER (UserJWTController)
    public UserRegisterResponse register(RegisterRequest registerRequest) {

        // Check1: e-mail if exist or not
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new RuntimeException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, registerRequest.getEmail()));
        }
        // encode the string password as crypted
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

        // Check2: First registration role of user is Member, so it should be added into the tbl_roles
        // before registration and assign it to role of the registering user
        Role role = roleRepository.findByName(RoleType.ROLE_MEMBER).orElseThrow(() -> new RuntimeException(
                String.format(ErrorMessage.ROLE_NOT_FOUND_MESSAGE, RoleType.ROLE_MEMBER.name())));

        Set<Role> roles = new HashSet<>();

        roles.add(role);


        // Creation Date should be now
        LocalDateTime today = LocalDateTime.now();

        User user = new User();

        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setScore(0); // default 0
        user.setAddress(registerRequest.getAddress());
        user.setPhone(registerRequest.getPhone());
        user.setBirthDate(registerRequest.getBirthDate());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setCreateDate(today);
        user.setRoles(roles);

        userRepository.save(user);

        // to get userId we need a query
        User registeredUser = userRepository.findByEmail(registerRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, registerRequest.getEmail())));

        return userMapper.userToUserRegisterResponse(registeredUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, id)));
    }

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.map(users);
    }

    public Page<UserResponse> getUserPage(String q, Pageable pageable) {

        Page<UserResponse> usersWithPage = null;
        if (!q.isEmpty()) {
            usersWithPage = userRepository.getAllUserWithQAdmin(q, pageable);
        } else {
            usersWithPage = userRepository.findAllWithPage(pageable);
        }

        return usersWithPage;
    }

    public UserResponse findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(
                com.library.exception.message.ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)

        ));

        return userMapper.userToUserResponse(user);
    }

    @Transactional
    public UserResponse userUpdate(Long updaterId, Long id, UpdateUserRequest updateUserRequest) {
        // Check1: control if the user exist or not with the requsted id
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        // get updater user info
        User updaterUser = userRepository.findById(updaterId).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, updaterId)));

        // Check2: control the updater is authorized or not for updating the requested user
        if (!updaterUser.getRoles().contains("ROLE_ADMIN")) {
            if (user.getRoles().contains("ROLE_STAFF") || user.getRoles().contains("ROLE_ADMIN"))
                throw new BadRequestException("Employee can only update members");
        }
        // Check3: BuiltIn control ->  only Admin can update if the builtIn is true of user
        if (user.getBuiltIn()) {
            if (!updaterUser.getRoles().contains("ROLE_ADMIN")) {
                throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
            }
        }

        Boolean emailExists = userRepository.existsByEmail(updateUserRequest.getEmail());

        // Check4: if the new email is belongs to another user in db
        if (emailExists && !updateUserRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, user.getEmail()));
        }

        user.setId(id);
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setAddress(updateUserRequest.getAddress());
        user.setPhone(updateUserRequest.getPhone());
        user.setBirthDate(updateUserRequest.getBirthDate());
        user.setEmail(updateUserRequest.getEmail());
        user.setScore(updateUserRequest.getScore());
        user.setRoles(updateUserRequest.getRoles());
        if (!updaterUser.getRoles().contains("ROLE_ADMIN")){
            user.setBuiltIn(updateUserRequest.getBuiltIn());
        }


        if (updateUserRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        userRepository.save(user);

        return userMapper.userToUserResponse(user);
    }


    public UserResponse deleteUser(Long id) {

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        // Check1: if user is exist or not
        if (loanRepository.existsByUserLoan(user)) {
            throw new ConflictException(String.format(ErrorMessage.USER_NOT_DELETED_LOAN_MESSAGE));
        }

        // Check2 : if user is builtIn, it can not be deleted by anyone
        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        userRepository.delete(user);
        return userMapper.userToUserResponse(user);

    }


    // 1- REGISTER a USER (UserJWTController)
    public UserRegisterResponse userCreate(UserCreateRequest userCreateRequest) {

        // Check1: e-mail if exist or not
        if (userRepository.existsByEmail(userCreateRequest.getEmail())) {
            throw new RuntimeException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, userCreateRequest.getEmail()));
        }
        // encode the string password as crypted
        String encodedPassword = passwordEncoder.encode(userCreateRequest.getPassword());




        // Creation Date should be now
        LocalDateTime today = LocalDateTime.now();

        User user = new User();

        user.setFirstName(userCreateRequest.getFirstName());
        user.setLastName(userCreateRequest.getLastName());
        user.setScore(0); // default 0
        user.setAddress(userCreateRequest.getAddress());
        user.setPhone(userCreateRequest.getPhone());
        user.setBirthDate(userCreateRequest.getBirthDate());
        user.setEmail(userCreateRequest.getEmail());
        user.setPassword(encodedPassword);
        user.setCreateDate(today);
        user.setRoles(userCreateRequest.getRoles());

        userRepository.save(user);

        // to get userId we need a query
        User registeredUser = userRepository.findByEmail(userCreateRequest.getEmail()).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessage.USER_NOT_FOUND_MESSAGE, userCreateRequest.getEmail())));

        return userMapper.userToUserRegisterResponse(registeredUser);
    }


    // Update Authenticated User (Update Own Information)
    public UserResponse userAuthUpdate(Long id, UpdateUserRequest updateUserRequest) {

        // Check1: control if the user exist or not with the requsted id
        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));


        // Check3: BuiltIn control ->  only Admin can update if the builtIn is true of user
        if (user.getBuiltIn()) {
            if (!user.getRoles().contains("ROLE_ADMIN")) {
                throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
            }
        }

        Boolean emailExists = userRepository.existsByEmail(updateUserRequest.getEmail());

        // Check4: if the new email is belongs to another user in db
        if (emailExists && !updateUserRequest.getEmail().equals(user.getEmail())) {
            throw new ConflictException(String.format(ErrorMessage.EMAIL_ALREADY_EXIST, user.getEmail()));
        }

        user.setId(id);
        user.setFirstName(updateUserRequest.getFirstName());
        user.setLastName(updateUserRequest.getLastName());
        user.setAddress(updateUserRequest.getAddress());
        user.setPhone(updateUserRequest.getPhone());
        user.setBirthDate(updateUserRequest.getBirthDate());
        user.setEmail(updateUserRequest.getEmail());


        if (updateUserRequest.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
        }

        userRepository.save(user);

        return userMapper.userToUserResponse(user);

    }

    // Update authenticated user password
    public void updateAuthPassword(Long id, UpdatePasswordRequest passwordRequest) {

        Optional<User> userOpt = userRepository.findById(id);
        User user = userOpt.get();


        if (user.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }


        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException(ErrorMessage.PASSWORD_NOT_MATCHED);
        }

        String hashedPassword = passwordEncoder.encode(passwordRequest.getNewPassword());
        user.setPassword(hashedPassword);

        userRepository.save(user);

    }
}
