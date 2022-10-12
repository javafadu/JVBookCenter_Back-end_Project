package com.library.controller;

import com.library.domain.Book;
import com.library.domain.User;
import com.library.dto.LoanDTO;
import com.library.dto.request.BookRequest;
import com.library.dto.request.LoanRequest;
import com.library.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {


    private LoanService loanService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createLoan(@Valid @RequestBody LoanRequest loanRequest) {

        loanService.saveLoan(loanRequest);

        Map<String, String> map = new HashMap<>();
        map.put("message", "Loan Successfully Created");
        map.put("status", "true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);


    }

}
