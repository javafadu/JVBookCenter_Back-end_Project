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

        // CONTROLS

        // 1- Book loanable or not
        // 2- Any book not returned in time (zamanında getirilmeyen kitap varsa)
        // 3- Not returned loans (which have still time to expire)





        // RESULTS



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

        LoanSaveResponse loanSaveResponse = new LoanSaveResponse();

        if(!book.getLoanable()) {
            throw new RuntimeException(book.getName()+"is already booked by someone");
        } else if (loanRepository.findNotReturnedInTime(user.getId())>0) {

            throw new RuntimeException(user.getFirstName()+" "+user.getLastName()+" has not returned a book / books in time");
        }
        else if (loanRepository.findUnreturnedLoansStillHaveTime(user.getId())>=bookRights) {
            throw new RuntimeException(user.getFirstName()+" "+user.getLastName()+" has not right to loan a new book");

        } else
        {
            loanSaveResponse.setResultMessage(book.getName()+" has been reserved by "+user.getFirstName()+" "+user.getLastName() );
            loanSaveResponse.setLoanDate(today);
            loanSaveResponse.setLoanedBook(book);
            loanSaveResponse.setNotes(loanSaveRequest.getNotes());
            loanSaveResponse.setExpireDate(today.plusDays(expireDays));
            loanSaveResponse.setUserId(loanSaveRequest.getUserId());

            loanRepository.save(loan);

            bookService.updateBookLoanable(book.getId());
        }

        //TODO yukardkiler için exception handler sistemi uyarlanacak

        return loanSaveResponse;

    }


    public Integer loanedBookByUser(Long id) {

        //   return loanRepository.findUnReturnedLoansNumber(id);

        return null;

    }

}
