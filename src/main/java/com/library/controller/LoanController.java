package com.library.controller;



import com.library.dto.request.LoanSaveRequest;
import com.library.dto.request.LoanUpdateRequest;
import com.library.dto.response.*;
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
@CrossOrigin
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

    // 2- GET OWN Loans of authenticated user (return related book object)
    // endpoint: [{server_url}/loans

    @GetMapping()
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<Page<LoanAuthResponseWithBook>> getAuthLoansWithPage (HttpServletRequest request,
                                                                                @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                                @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                                @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                                                @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Long userId = (Long) request.getAttribute("id");
        Page<LoanAuthResponseWithBook> authLoans = loanService.getLoansAuthWithPages(userId, pageable);

        return ResponseEntity.ok(authLoans);
    }


    // 3- GET OWN any loan details of authenticated user
    // endpoint: [{server_url}/loans/{id}
    @GetMapping("/{loanId}")
    @PreAuthorize("hasRole('MEMBER')")
    public ResponseEntity<LoanAuthResponseWithBook> getAuthLoanWithId (HttpServletRequest request, @PathVariable Long loanId) {
        Long userId = (Long) request.getAttribute("id");
        LoanAuthResponseWithBook authLoanWithId = loanService.getLoanAuthWithId(userId,loanId);
        return ResponseEntity.ok(authLoanWithId);
    }

    // 4- GET LOANS of SPECIFIED userId
    // endpoint: [{server_url}/loans/user/{id}
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<LoanAdminResponseWithBook>> getUserLoansWithPage (@PathVariable Long userId,
                                                                                 @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                                 @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                                                 @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<LoanAdminResponseWithBook> userLoans = loanService.getUserLoansWithPages(userId, pageable);

        return ResponseEntity.ok(userLoans);
    }


    // 5- GET LOANS of SPECIFIED bookId
    // endpoint: [{server_url}/loans/book/{id}
    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<LoanAdminResponseWithUser>> getBookLoansWithPage (@PathVariable Long bookId,
                                                                                 @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                                 @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                                 @RequestParam(required = false,value = "sort", defaultValue = "loanDate") String prop,
                                                                                 @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<LoanAdminResponseWithUser> bookLoans = loanService.getBookLoansWithPages(bookId, pageable);

        return ResponseEntity.ok(bookLoans);
    }

    // 6- GET specified LOAN details including book and user objects
    // endpoint: [{server_url}/loans/auth/{id}
    @GetMapping("/auth/{loanId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<LoanAdminResponseWithUserAndBook> getLoanDetailsWithPage (@PathVariable Long loanId) {

        LoanAdminResponseWithUserAndBook loanDetails = loanService.getLoanDetailsWithPage(loanId);

        return ResponseEntity.ok(loanDetails);
    }

    // 7- UPDATE the selected loan
    // endpoint: [{server_url}/loans/{id}
    @PutMapping("/{loanId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<LoanUpdateResponse> updateLoan(@Valid @PathVariable Long loanId, @RequestBody LoanUpdateRequest loanUpdateRequest) {

        LoanUpdateResponse loanUpdateResponse = loanService.updateLoan(loanId, loanUpdateRequest);

        return new ResponseEntity<>(loanUpdateResponse, HttpStatus.CREATED);

    }




}
