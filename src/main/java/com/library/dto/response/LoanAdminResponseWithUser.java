package com.library.dto.response;


import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanAdminResponseWithUser {


    private Long id;
    private Long userId;
    private Long bookId;
    private User user;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;
    private String notes;



    // MEMBERS CAN NOT SEE THE Notes
    public LoanAdminResponseWithUser(Loan loan) {
        this.id = loan.getId();
        this.userId = loan.getUserLoan().getId();
        this.bookId = loan.getLoanedBooks().getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.user = loan.getUserLoan();
        this.notes = loan.getNotes();
    }





}