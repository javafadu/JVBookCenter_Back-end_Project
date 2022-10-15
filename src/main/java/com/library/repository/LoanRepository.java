package com.library.repository;

import com.library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {


   @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL  and l.user_id= :id)",
            nativeQuery = true)
            Integer findUnreturnedLoansStillHaveTime(@Param("id") Long id);

    @Query(
            value = "SELECT Count(*) FROM tbl_loans l WHERE (l.return_date IS NULL  and l.user_id= :id)",
            nativeQuery = true)
    Integer findNotReturnedInTime(@Param("id") Long id);

}
