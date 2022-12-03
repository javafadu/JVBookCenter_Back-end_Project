package com.library.dto.response;


import com.library.domain.Book;
import com.library.domain.ImageFile;
import com.library.domain.Loan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    private Set<String> bookImage;




    // MEMBERS CAN NOT SEE THE Notes
    public LoanAuthResponseWithBook(Loan loan) {
        this.id = loan.getId();
        this.userId = loan.getUserLoan().getId();
        this.bookId = loan.getLoanedBooks().getId();
        this.loanDate = loan.getLoanDate();
        this.expireDate = loan.getExpireDate();
        this.returnDate = loan.getReturnDate();
        this.book = loan.getLoanedBooks();
        this.bookImage=getImageId(loan.getLoanedBooks().getImage());
    }

    public Set<String> getImageId(Set<ImageFile> images){
        Set<String> imgStrSet=new HashSet<>();
        imgStrSet=images.stream().map(image->image.getId().toString()).collect(Collectors.toSet());
        return imgStrSet;
    }



}