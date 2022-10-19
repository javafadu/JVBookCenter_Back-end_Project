package com.library.repository;

import com.library.domain.Book;
import com.library.domain.Loan;
import com.library.dto.response.LoanAuthPagesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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


    @Query("SELECT new com.library.dto.response.LoanAuthPagesResponse(l)  FROM Loan l where l.userLoan.id=?1")
    Page<LoanAuthPagesResponse> getAuthUserLoansWithPage(Long id, Pageable pageable);



}
