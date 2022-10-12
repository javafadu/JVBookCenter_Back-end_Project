package com.library.dto.request;

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
public class LoanRequest {


        @NotNull(message = "Please provide userId")
        private User userId;

        @NotNull(message = "Please provide bookId")
        private Book bookId;

        @Size(max = 300)
        private String notes;








}
