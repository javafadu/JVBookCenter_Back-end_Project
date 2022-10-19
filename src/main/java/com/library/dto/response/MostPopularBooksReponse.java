package com.library.dto.response;

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
public class MostPopularBooksReponse {
    private Long id;
    private String name;
    private String isbn;
    private Integer countBooks;

}
