package com.library.controller;


import com.library.dto.request.LoanSaveRequest;
import com.library.dto.response.LoanAuthPagesResponse;
import com.library.dto.response.LoanSaveResponse;
import com.library.service.LoanService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@RestController
@RequestMapping("/loans")
@AllArgsConstructor
public class LoanController {


    private LoanService loanService;


    // 1- LOAN A BOOK FOR A REGISTERED USER With userId, bookId
    // endpoint: [{server_url}/loans
    /* Json body
    {
    "userId": 3,
    "bookId": 4,
    "notes": "Ahmet reserved Jane Eyre"
    }
     */

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<LoanSaveResponse> createLoan(@Valid @RequestBody LoanSaveRequest loanSaveRequest) {

        LoanSaveResponse loanSaveResponse = loanService.saveLoan(loanSaveRequest);

        return new ResponseEntity<>(loanSaveResponse, HttpStatus.CREATED);

    }

    // 1- GET OWN Loans of authenticated user
    // endpoint: [{server_url}/loans
    /* Json body
    {
    "userId": 3,
    "bookId": 4,
    "notes": "Ahmet reserved Jane Eyre"
    }
     */

    @GetMapping()
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Page<LoanAuthPagesResponse>> getAuthLoansWithPage (HttpServletRequest request,
                                                                             @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                             @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                                             @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Long userId = (Long) request.getAttribute("id");
        Page<LoanAuthPagesResponse> authLoans = loanService.getLoansAuthWithPages(userId, pageable);

        return ResponseEntity.ok(authLoans);
    }

}
