package com.library.dto.response;


import com.library.domain.Book;
import com.library.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoanAuthResponseWithBook {


    private Long id;
    private Long userId;
    private Long bookId;
    private Book book;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;


    // ADMIN and STAFF CAN SEE Notes
 //  public LoanAuthPagesResponse(Long id, Long userId, Long bookId, LocalDateTime loanDate, LocalDateTime expireDate, LocalDateTime returnDate, String notes) {
 //      this.id = id;
 //      this.userId = userId;
 //      this.bookId = bookId;
 //      this.loanDate = loanDate;
 //      this.expireDate = expireDate;
 //      this.returnDate = returnDate;
 //      this.notes = notes;
 //  }

    // MEMBERS CAN NOT SEE THE Notes
    public LoanAuthResponseWithBook(Loan loan) {
        this.id = loan.getId();
        this.userId = loan.getUserLoan().getId();
        this.bookId = loan.getLoanedBooks().getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.book = loan.getLoanedBooks();
    }





}