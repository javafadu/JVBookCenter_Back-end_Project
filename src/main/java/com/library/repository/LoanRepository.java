package com.library.repository;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.domain.User;
import com.library.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByLoanedBooks(Book book);
    boolean existsByUserLoan(User user);



    // All unreturned loaned book numbers all
    @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) > CAST(CURRENT_DATE AS DATE))",
            nativeQuery = true)
    Integer unreturnedBookNumbers();


    // All unreturned loaned book numbers of the user
   @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) > CAST(CURRENT_DATE AS DATE) and l.user_id= :id)",
            nativeQuery = true)
            Integer unreturnedBookNumbersOfUser(@Param("id") Long id);


    // All unreturned and expired loaned book numbers all
    @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) < CAST(CURRENT_DATE AS DATE))",
            nativeQuery = true)
    Integer expiredBookNumbers();

    // All unreturned and expired loaned book numbers of the user
    @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) < CAST(CURRENT_DATE AS DATE)  and l.user_id= :id)",
            nativeQuery = true)
    Integer expiredBookNumbersOfUser(@Param("id") Long id);


    @Query("SELECT new com.library.dto.response.LoanAuthResponseWithBook(l)  FROM Loan l where l.userLoan.id=?1")
    Page<LoanAuthResponseWithBook> getAuthUserLoansWithPage(Long id, Pageable pageable);

    @Query("SELECT new com.library.dto.response.LoanAuthResponseWithBook(l)  FROM Loan l where l.id=?1 and l.userLoan.id=?2")
    LoanAuthResponseWithBook getAuthUserLoanWithId(Long loanId, Long userId);

    @Query("SELECT new com.library.dto.response.LoanAdminResponseWithBook(l)  FROM Loan l where l.userLoan.id=?1")
    Page<LoanAdminResponseWithBook> getUserLoansWithPage(Long userId, Pageable pageable);

    @Query("SELECT new com.library.dto.response.LoanAdminResponseWithUser(l)  FROM Loan l where l.loanedBooks.id=?1")
    Page<LoanAdminResponseWithUser> getBookLoansWithPage(Long bookId, Pageable pageable);

    @Query("SELECT new com.library.dto.response.LoanAdminResponseWithUserAndBook(l)  FROM Loan l where l.id=?1")
    LoanAdminResponseWithUserAndBook getLoanDetails(Long loanId);


    @Query("SELECT l.loanedBooks.id, l.loanedBooks.name, l.loanedBooks.isbn, COUNT(l.loanedBooks.id) as sumLoan FROM Loan AS l GROUP BY l.loanedBooks.id, l.loanedBooks.name, l.loanedBooks.isbn ORDER BY sumLoan DESC ")
    Page<Object[]> mostPopularBooks(Integer amount, Pageable pageable);


    @Query("SELECT new com.library.dto.response.ReportBookResponse(l) FROM Loan AS l WHERE l.returnDate IS NULL ")
    Page<ReportBookResponse> unReturnedBooks(Pageable pageable);

    @Query("SELECT new com.library.dto.response.ReportBookResponse(l) FROM Loan AS l WHERE l.returnDate IS NULL and l.expireDate > :today ")
    Page<ReportBookResponse> expiredBooks(@Param("today") LocalDateTime today, Pageable pageable);

    @Query("SELECT l.userLoan.id, l.userLoan.firstName,l.userLoan.lastName, COUNT(l.userLoan.id) as sumLoan FROM Loan AS l GROUP BY l.userLoan.id, l.userLoan.firstName,l.userLoan.lastName ORDER BY sumLoan DESC ")
    Page<Object[]> mostBorrowers(Pageable pageable);
}
