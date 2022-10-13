package com.library.controller;


import com.library.dto.request.RegisterRequest;
import com.library.dto.response.LResponse;
import com.library.dto.response.ResponseMessages;
import com.library.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserJWTController {

    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<LResponse> register(@Valid @RequestBody RegisterRequest registerRequest){
        userService.register(registerRequest);

        LResponse response = new LResponse();
        response.setMessage(ResponseMessages.REGISTER_RESPONSE_MESSAGE);
        response.setSuccess(true);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
