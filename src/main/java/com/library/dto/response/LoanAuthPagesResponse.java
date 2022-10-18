package com.library.dto.response;


import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.repository.BookRepository;
import com.library.service.BookService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Getter
@Setter
public class LoanAuthPagesResponse {


    private Long id;
    private Long userId;
    private Long bookId;
    private Book book;
    private LocalDateTime loanDate;
    private LocalDateTime expireDate;
    private LocalDateTime returnDate;
    private String notes;

    // ADMIN and STAFF CAN SEE Notes
    public LoanAuthPagesResponse(Long id, Long userId, Long bookId, LocalDateTime loanDate, LocalDateTime expireDate, LocalDateTime returnDate, String notes) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.expireDate = expireDate;
        this.returnDate = returnDate;
        this.notes = notes;
    }

    // MEMBERS CAN NOT SEE THE Notes
    public LoanAuthPagesResponse(Long id, Long userId, Long bookId, LocalDateTime loanDate, LocalDateTime expireDate, LocalDateTime returnDate, Book book) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
        this.loanDate = loanDate;
        this.expireDate = expireDate;
        this.returnDate = returnDate;
        this.book = book;

    }



}