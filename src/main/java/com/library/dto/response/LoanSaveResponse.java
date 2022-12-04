package com.library.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.library.domain.Book;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LoanSaveResponse {


    private Long userId;

    private String bookName;

    private LocalDateTime loanDate;

    private LocalDateTime expireDate;

    private LocalDateTime returnDate;

    private String notes;

    private String resultMessage;


    public LoanSaveResponse(Book book) {
        this.userId = userId;
        this.bookName = book.getName();
        this.loanDate = loanDate;
        this.expireDate = expireDate;
        this.returnDate = returnDate;
        this.notes = notes;
        this.resultMessage = resultMessage;
    }
}
