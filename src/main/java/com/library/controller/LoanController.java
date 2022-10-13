package com.library.controller;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.LoanDTO;
import com.library.dto.request.BookRequest;
import com.library.dto.request.LoanRequest;
import com.library.dto.response.LoanResponse;
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


    @PostMapping("/add")
    public ResponseEntity<LoanResponse> createLoan(@Valid @RequestBody LoanRequest loanRequest) {

        LoanResponse loanResponse = new LoanResponse();
        loanResponse = loanService.saveLoan(loanRequest);



        return new ResponseEntity<>(loanResponse, HttpStatus.CREATED);


    }

}
