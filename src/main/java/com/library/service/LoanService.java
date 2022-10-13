package com.library.service;



import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.request.LoanRequest;
import com.library.dto.response.LoanResponse;
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


    public LoanResponse saveLoan(LoanRequest loanRequest){

        Loan loan = new Loan();

        User user = new User();
        user = userService.getUserById(loanRequest.getUserId());
        Integer score = user.getScore();


        Integer expireDays =0;
        Integer bookRights=0;

        if(score>=2) {
            bookRights=5;
            expireDays=20;
        } else if(score==1) {
            bookRights=4;
            expireDays=15;
        }
        else if(score==0) {
            bookRights=3;
            expireDays=10;
        }else if(score==-1) {
            bookRights=2;
            expireDays=6;
        } else {
            bookRights=1;
            expireDays=3;
        }

        loan.setUserLoan(user);

        Book book = new Book();
        book = bookService.getBookById(loanRequest.getBookId());

        loan.setLoanedBooks(book);

        LocalDateTime today = LocalDateTime.now();
        loan.setLoanDate(today);

        loan.setExpireDate(today.plusDays(expireDays));
        loan.setNotes(loanRequest.getNotes());


        Integer loanedBookCount = loanedBookByUser(loanRequest.getUserId());

     //   if(bookRights>=loanedBookCount) {
     //       throw new RuntimeException("Not found");
     //   }

     loanRepository.save(loan);

        LoanResponse loanResponse = new LoanResponse();

        loanResponse.setLoanDate(today);
       loanResponse.setLoanedBookName(book.getName());
       loanResponse.setNotes(loanRequest.getNotes());
       loanResponse.setExpireDate(today.plusDays(expireDays));
        loanResponse.setUserId(loanRequest.getUserId());
        loanResponse.setNotes(loanRequest.getNotes());


        return loanResponse;


    }


    public Integer loanedBookByUser(Long id) {

     //   return loanRepository.findUnReturnedLoansNumber(id);

        return null;

    }

}
