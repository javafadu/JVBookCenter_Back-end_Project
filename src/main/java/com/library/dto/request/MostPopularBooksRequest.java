package com.library.dto.request;

import com.library.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MostPopularBooksRequest {

    @NotNull(message = "Please provide number of books to return")
    private Integer amount;

    private Integer page;

    private Integer size;

    private Integer countBooks;

}
