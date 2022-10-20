package com.library.dto.response;

import com.library.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReportBookResponse {
    private Long id;
    private String name;
    private String isbn;

    public ReportBookResponse(Loan loan) {
        this.id = loan.getLoanedBooks().getId();
        this.name = loan.getLoanedBooks().getName();
        this.isbn = loan.getLoanedBooks().getIsbn();
    }
}
