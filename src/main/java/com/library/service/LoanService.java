package com.library.service;


import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.request.LoanSaveRequest;
import com.library.dto.response.LoanAuthPagesResponse;
import com.library.dto.response.LoanSaveResponse;

import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class LoanService {

    LoanRepository loanRepository;
    UserService userService;
    BookService bookService;


    // 1- SAVE LOAN WITH a bookId FOR A MEMBER (userId)
    public LoanSaveResponse saveLoan(LoanSaveRequest loanSaveRequest) {

        // CONTROLS

        // 1- Book loanable or not
        // 2- Any book not returned in time (is there any book expired status in loans and not returned yet)
        // 3- User has right to loan new book (bookRights>not returned loaned books(not expired yet))


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


        //   if(bookRights>=loanedBookCount) {
        //       throw new RuntimeException("Not found");
        //   }

        LoanSaveResponse loanSaveResponse = new LoanSaveResponse();

        if (!book.getLoanable()) {
            throw new RuntimeException(book.getName() + "is already booked by someone");
        } else if (loanRepository.findNotReturnedInTime(user.getId()) > 0) {
            throw new RuntimeException(String.format(ErrorMessage.THERE_ARE_EXPIRED_BOOKS_FOR_THIS_USER, loanRepository.findNotReturnedInTime(user.getId()), user.getFirstName() + " " + user.getLastName()));
        } else if (loanRepository.findUnreturnedLoansStillHaveTime(user.getId()) >= bookRights) {
            throw new RuntimeException(String.format(ErrorMessage.HAS_NOT_RIGHT_TO_LOAN_BOOK, user.getFirstName() + " " + user.getLastName()));

        } else {
            loanSaveResponse.setResultMessage(book.getName() + " has been reserved by " + user.getFirstName() + " " + user.getLastName());
            loanSaveResponse.setLoanDate(today);
            loanSaveResponse.setLoanedBook(book);
            loanSaveResponse.setNotes(loanSaveRequest.getNotes());
            loanSaveResponse.setExpireDate(today.plusDays(expireDays));
            loanSaveResponse.setUserId(loanSaveRequest.getUserId());

            loanRepository.save(loan);

            bookService.updateBookLoanable(book.getId());
        }


        return loanSaveResponse;

    }

    // 2- GET ALL OWN LOANS of AUTH USER
    @Transactional
    public Page<LoanAuthPagesResponse> getLoansAuthWithPages(Long userId, Pageable pageable ) {
        User user= userService.getUserById(userId);
        Page<LoanAuthPagesResponse> authLoansWithPage = loanRepository.getAuthUserLoansWithPage(userId,pageable);

        if(authLoansWithPage.isEmpty()) throw new ResourceNotFoundException("Not found");

        return authLoansWithPage;
    }




}
