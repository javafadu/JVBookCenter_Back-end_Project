package com.library.controller;


import com.library.domain.User;
import com.library.dto.request.LoginRequest;
import com.library.dto.request.RegisterRequest;
import com.library.dto.response.LResponse;
import com.library.dto.response.LoginResponse;
import com.library.dto.response.ResponseMessages;
import com.library.dto.response.UserRegisterResponse;
import com.library.security.jwt.JwtUtils;
import com.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    // no need Autowired, we added @AllArgConstruction
    private AuthenticationManager authManager;
    // CRUD operation for user process
    private JwtUtils jwtUtils;
    // create token and response to the client side


    // 1- ADD ROLES from enum to DB, before registering a user.
    // http://localhost:8080/addroles
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@Valid @RequestBody LoginRequest loginRequest) {

        // STEP1 : get username and password and authenticate
        // (using AuthenticationManager in WebSecurityConfig)
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());
        Authentication authentication = authManager.authenticate(authToken);

        // STEP2 : no exception in Step2, means successfully login, generate Jwt Token
        String token = jwtUtils.generateJwtToken(authentication);

        LoginResponse response = new LoginResponse();
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
        // no insert operation so status is OK

    }
}
