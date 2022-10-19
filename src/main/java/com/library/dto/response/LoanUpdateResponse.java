package com.library.dto.response;

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

public class LoanUpdateResponse {

    private Long id;

    private Long userId;

    private Long bookId;

    private LocalDateTime loanDate;

    private LocalDateTime expireDate;

    private LocalDateTime returnDate;

    private String notes;





}
