package com.library.service;



import com.library.domain.Loan;
import com.library.dto.request.LoanRequest;
import com.library.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@AllArgsConstructor
public class LoanService {

    LoanRepository loanRepository;

    public void saveLoan(LoanRequest loanRequest){

        Loan loan = new Loan();


        loan.setLoanedBooks(loanRequest.getBookId());
        loan.setUserLoan(loanRequest.getUserId());
        loan.setNotes(loanRequest.getNotes());

     loanRepository.save(loan);



    }

}
