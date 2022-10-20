package com.library.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportGeneralResponse {

    private Integer books;
    private Integer authors;
    private Integer publishers;
    private Integer categories;
    private Integer loans;
    private Integer unReturnedBooks;
    private Integer expiredBooks;
    private Integer members;

}
