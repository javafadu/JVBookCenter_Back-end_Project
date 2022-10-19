package com.library.service;


import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.request.LoanSaveRequest;
import com.library.dto.request.LoanUpdateRequest;
import com.library.dto.response.*;

import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    // 2- GET ALL OWN LOANS (Page) of any USER with userId, pageable
    @Transactional
    public Page<LoanAuthResponseWithBook> getLoansAuthWithPages(Long userId, Pageable pageable ) {
        User user= userService.getUserById(userId);
        Page<LoanAuthResponseWithBook> authLoansWithPage = loanRepository.getAuthUserLoansWithPage(userId,pageable);

        if(authLoansWithPage.isEmpty()) throw new ResourceNotFoundException("Not found");

        return authLoansWithPage;
    }


    // 3- GET A LOAN of User with userId, loanId
    @Transactional
    public LoanAuthResponseWithBook getLoanAuthWithId(Long userId, Long loanId) {
        LoanAuthResponseWithBook loanDetail = loanRepository.getAuthUserLoanWithId(loanId, userId);

        if(loanDetail == null)  throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,loanId));

        return loanDetail;
    }

    // 4- GET loans of SPECIFIED userId
    @Transactional
    public Page<LoanAdminResponseWithBook> getUserLoansWithPages(Long userId, Pageable pageable) {

        Page<LoanAdminResponseWithBook> userLoansWithPage = loanRepository.getUserLoansWithPage(userId,pageable);

        if(userLoansWithPage.isEmpty()) throw new ResourceNotFoundException("Not found");

        return userLoansWithPage;
    }

    // 5- GET loans of SPECIFIED bookId
    @Transactional
    public Page<LoanAdminResponseWithUser> getBookLoansWithPages(Long bookId, Pageable pageable) {

        Page<LoanAdminResponseWithUser> bookLoansWithPage = loanRepository.getBookLoansWithPage(bookId,pageable);

        if(bookLoansWithPage.isEmpty()) throw new ResourceNotFoundException("Not found");

        return bookLoansWithPage;
    }

    // 6- GET loan details including user and book object
    public LoanAdminResponseWithUserAndBook getLoanDetailsWithPage(Long loanId) {

        LoanAdminResponseWithUserAndBook loanDetails = loanRepository.getLoanDetails(loanId);

        if(loanDetails==null) throw new ResourceNotFoundException("Not found");

        return loanDetails;

    }

    // 7- Update a loan
    public LoanUpdateResponse updateLoan(Long loanId, LoanUpdateRequest loanUpdateRequest) {
        // CONTROLS
        // return date is not null

        Loan loan = loanRepository.findById(loanId).orElseThrow(()-> new ResourceNotFoundException("Loan is not found"));

        if(loanUpdateRequest==null) throw new BadRequestException("The updated body is null");

        Book book = getBookInfoInLoan(loanId);



        if(loanUpdateRequest.getReturnDate()!=null) {
            // change the loanable in related book
            loan.setId(loan.getId());
            loan.setNotes(loanUpdateRequest.getNotes());
            loan.setExpireDate(loanUpdateRequest.getExpireDate());
            loan.setReturnDate(loanUpdateRequest.getReturnDate());

            loanRepository.save(loan);
            bookService.updateBookLoanable(book.getId());

        } else {
            loan.setId(loan.getId());
            loan.setNotes(loanUpdateRequest.getNotes());
            loan.setExpireDate(loanUpdateRequest.getExpireDate());

            loanRepository.save(loan);
        }

        Loan updatedLoan = loanRepository.findById(loanId).orElseThrow(()-> new ResourceNotFoundException("Loan is not found"));

        LoanUpdateResponse loanUpdateResponse = new LoanUpdateResponse();

        loanUpdateResponse.setId(updatedLoan.getId());
        loanUpdateResponse.setBookId(updatedLoan.getLoanedBooks().getId());
        loanUpdateResponse.setLoanDate(updatedLoan.getLoanDate());
        loanUpdateResponse.setUserId(updatedLoan.getUserLoan().getId());
        loanUpdateResponse.setReturnDate(updatedLoan.getReturnDate());
        loanUpdateResponse.setExpireDate(updatedLoan.getExpireDate());
        loanUpdateResponse.setNotes(updatedLoan.getNotes());

        return loanUpdateResponse;

    }

    // GET BOOK OBJECT from Related Loan
    public Book getBookInfoInLoan (Long id) {
        Loan loan = loanRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

        Book book = loan.getLoanedBooks();
        if(book == null) throw new ResourceNotFoundException("The loaned book is not found");

        return book;

    }



}
