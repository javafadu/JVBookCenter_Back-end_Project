package com.library.controller;

import com.library.dto.request.LoanSaveRequest;
import com.library.dto.response.LoanSaveResponse;
import com.library.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {


    private LoanService loanService;


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    public ResponseEntity<LoanSaveResponse> createLoan(@Valid @RequestBody LoanSaveRequest loanSaveRequest) {

        LoanSaveResponse loanSaveResponse = loanService.saveLoan(loanSaveRequest);

        return new ResponseEntity<>(loanSaveResponse, HttpStatus.CREATED);


    }

}
