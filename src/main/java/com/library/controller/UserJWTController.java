package com.library.controller;


import com.library.domain.User;
import com.library.dto.request.RegisterRequest;
import com.library.dto.response.LResponse;
import com.library.dto.response.ResponseMessages;
import com.library.dto.response.UserRegisterResponse;
import com.library.security.jwt.JwtUtils;
import com.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {

    private UserService userService;


    // 1- ADD ROLES from enum to DB, before registering a user.
    // http://localhost:8085/addroles
    @GetMapping("/addroles")
    public ResponseEntity<Map<String,String>> addRoles() {

        List<String> existRolesList = userService.addRoles();

        Map<String, String> map = new HashMap<>();
        map.put("message", "All roles have been inserted into DB");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }


    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest){

        UserRegisterResponse userRegisterResponse = userService.register(registerRequest);

        return new ResponseEntity<>(userRegisterResponse, HttpStatus.CREATED);
    }
}
