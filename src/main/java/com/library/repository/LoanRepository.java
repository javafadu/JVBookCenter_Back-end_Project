package com.library.repository;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.dto.response.LoanAdminResponseWithBook;
import com.library.dto.response.LoanAdminResponseWithUser;
import com.library.dto.response.LoanAdminResponseWithUserAndBook;
import com.library.dto.response.LoanAuthResponseWithBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByLoanedBooks(Book book);

    // All unreturned loaned book numbers
   @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) > CAST(CURRENT_DATE AS DATE) and l.user_id= :id)",
            nativeQuery = true)
            Integer findUnreturnedLoansStillHaveTime(@Param("id") Long id);

    // All unreturned and expired loaned book numbers
    @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) < CAST(CURRENT_DATE AS DATE)  and l.user_id= :id)",
            nativeQuery = true)
    Integer findNotReturnedInTime(@Param("id") Long id);


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
}
