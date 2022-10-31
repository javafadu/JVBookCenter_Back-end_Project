package com.library.controller;


import com.library.dto.request.RegisterRequest;
import com.library.dto.request.UpdateUserRequest;
import com.library.dto.request.UserCreateRequest;
import com.library.dto.response.LoanAuthResponseWithBook;
import com.library.dto.response.UserRegisterResponse;
import com.library.dto.response.UserResponse;

import com.library.service.LoanService;
import com.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping()
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private LoanService loanService;

    // 4- GET Auth User Information
    // endpoint: [{server_url}/user
    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<UserResponse> getAuthUser(HttpServletRequest httpServletRequest){
        Long id = (Long) httpServletRequest.getAttribute("id");
        UserResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // 5- GET AUTH User Loans
    // endpoint: [{server_url}/user/loans
    @GetMapping("/user/loans")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<Page<LoanAuthResponseWithBook>> getAuthLoansWithPage (HttpServletRequest httpServletRequest,
                                                                                @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                                @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                                                @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Long userId = (Long) httpServletRequest.getAttribute("id");
        Page<LoanAuthResponseWithBook> authLoans = loanService.getLoansAuthWithPages(userId, pageable);

        return ResponseEntity.ok(authLoans);
    }


    // 6- GET All User with Paging by ADMIN or STAFF
    // endpoint: [{server_url}/users
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<UserResponse>> getAllUsersByPage(

            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "20") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "createDate") String prop,
            @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {

        Pageable pageable= PageRequest.of(page, size, Sort.by(direction,prop));

        String qLower = q.toLowerCase();

        Page<UserResponse> usersWithPage = userService.getUserPage(qLower, pageable);
        return  ResponseEntity.ok(usersWithPage);
    }

    // 7- GET a USER with ID by ADMIN or STAFF
    // endpoint: [{server_url}/users/{id}
    @GetMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id){
        UserResponse user = userService.findById(id);
        return ResponseEntity.ok(user);
    }

    // 8- Update A User by ADMIN or STAFF
    // endpoint: [{server_url}/users/{id}
    /* json Bodu

    {
    "firstName": "Walter",
    "lastName": "White",
    "address": "301 Milam St, Houston, TX 77002, United States",
    "phone": "713-600-2267",
    "birthDate":"01/30/1980",
    "email": "walter@mail.com0",
    "score": 0,
    "builtIn": true,
    "roles": [
                {
                    "id": 3,
                    "name": "ROLE_MEMBER"
                },
                {
                    "id": 1,
                    "name": "ROLE_ADMIN"
                }
            ]
    }

     */
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') ")
    public ResponseEntity<UserResponse> updateUser (HttpServletRequest httpServletRequest,@Valid @PathVariable Long id, @RequestBody UpdateUserRequest updateUserRequest){
        Long updaterId =(Long) httpServletRequest.getAttribute("id");

        UserResponse userResponse= userService.userUpdate(updaterId, id, updateUserRequest);

        return ResponseEntity.ok(userResponse);

    }

    // 9- Delete A User by ADMIN or STAFF
    // endpoint: [{server_url}/users/{id}
    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN') ")
    public ResponseEntity<UserResponse> deleteUser ( @PathVariable Long id){

        UserResponse userResponse= userService.deleteUser(id);

        return ResponseEntity.ok(userResponse);

    }




    // 11- Create a user by ADMIN or STAFF
    // endpoint: [{server_url}/users
    /*

        {
    "firstName": "John",
    "lastName": "Coffee",
    "address": "New York, United States",
    "phone": "555-500-2233",
    "birthDate":"01/30/1982",
    "email": "john@mail.com",
    "password": "12345",
    "roles": [
                {
                    "id": 3,
                    "name": "ROLE_MEMBER"
                },
                {
                    "id": 1,
                    "name": "ROLE_ADMIN"
                }
            ]

     }


     */
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') ")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody UserCreateRequest userCreateRequest){

        UserRegisterResponse userRegisterResponse = userService.userCreate(userCreateRequest);

        return new ResponseEntity<>(userRegisterResponse, HttpStatus.CREATED);
    }


}
