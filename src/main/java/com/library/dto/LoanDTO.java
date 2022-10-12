package com.library.dto;

import com.library.domain.Book;
import com.library.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoanDTO {

    @NotNull(message = "Please provide userId")
    private User userId;

    @NotNull(message = "Please provide bookId")
    private Book bookId;


    @NotNull(message = "Please provide loanDate")
    private LocalDateTime loanDate;

    @NotNull(message = "Please provide expireDate")
    private LocalDateTime expireDate;

    @NotNull(message = "Please provide returnDate")
    private LocalDateTime returnDate;

    @Size(max = 300)
    private String notes;
}

