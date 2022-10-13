package com.library.repository;

import com.library.domain.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {


 //   @Query(
 //           value = "SELECT Count(*) FROM tbl_loans l WHERE l.return_date is null and t.user_id=userId",
 //           nativeQuery = true)
 //   Integer findUnReturnedLoansNumber(Long userId);




}
