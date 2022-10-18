package com.library.repository;

import com.library.domain.Book;
import com.library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    boolean existsByLoanedBooks(Book book);

    //TODO where in içine expire_date>today eklenecek
   @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) > CAST(CURRENT_DATE AS DATE) and l.user_id= :id)",
            nativeQuery = true)
            Integer findUnreturnedLoansStillHaveTime(@Param("id") Long id);

    //TODO where içine expire_date<today eklenecek ///CAST(Submission_date AS DATE) = CAST(GETDATE() AS DATE)
    @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL and CAST(l.expire_date AS DATE) < CAST(CURRENT_DATE AS DATE)  and l.user_id= :id)",
            nativeQuery = true)
    Integer findNotReturnedInTime(@Param("id") Long id);

}
