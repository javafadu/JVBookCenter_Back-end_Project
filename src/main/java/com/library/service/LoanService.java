package com.library.service;


import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.request.LoanSaveRequest;
import com.library.dto.response.LoanSaveResponse;
import com.library.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class LoanService {

    LoanRepository loanRepository;
    UserService userService;
    BookService bookService;


    public LoanSaveResponse saveLoan(LoanSaveRequest loanSaveRequest) {

        Loan loan = new Loan();

        User user = new User();
        user = userService.getUserById(loanSaveRequest.getUserId());
        Integer score = user.getScore();


        Integer expireDays = 0;
        Integer bookRights = 0;

        if (score >= 2) {
            bookRights = 5;
            expireDays = 20;
        } else if (score == 1) {
            bookRights = 4;
            expireDays = 15;
        } else if (score == 0) {
            bookRights = 3;
            expireDays = 10;
        } else if (score == -1) {
            bookRights = 2;
            expireDays = 6;
        } else {
            bookRights = 1;
            expireDays = 3;
        }

        loan.setUserLoan(user);

        Book book = new Book();
        book = bookService.getBookById(loanSaveRequest.getBookId());

        loan.setLoanedBooks(book);

        LocalDateTime today = LocalDateTime.now();
        loan.setLoanDate(today);

        loan.setExpireDate(today.plusDays(expireDays));
        loan.setNotes(loanSaveRequest.getNotes());


        Integer loanedBookCount = loanedBookByUser(loanSaveRequest.getUserId());

        //   if(bookRights>=loanedBookCount) {
        //       throw new RuntimeException("Not found");
        //   }

        if (book.getActive() == true) {
            loanRepository.save(loan);


        } else {
            //TODO burasi revize edilecek yeni method da
        }

        LoanSaveResponse loanSaveResponse = new LoanSaveResponse();

        loanSaveResponse.setLoanDate(today);
        loanSaveResponse.setLoanedBookName(book.getName());
        loanSaveResponse.setNotes(loanSaveRequest.getNotes());
        loanSaveResponse.setExpireDate(today.plusDays(expireDays));
        loanSaveResponse.setUserId(loanSaveRequest.getUserId());
        loanSaveResponse.setNotes(loanSaveRequest.getNotes());


        return loanSaveResponse;


    }


    public Integer loanedBookByUser(Long id) {

        //   return loanRepository.findUnReturnedLoansNumber(id);

        return null;

    }

}
