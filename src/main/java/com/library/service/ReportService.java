package com.library.service;

import com.library.dto.response.ReportGeneralResponse;
import com.library.dto.response.ReportBookResponse;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportService {

    private BookRepository bookRepository;
    private UserRepository userRepository;
    private LoanRepository loanRepository;
    private PublisherRepository publisherRepository;
    private AuthorRepository authorRepository;
    private CategoryRepository categoryRepository;



    public ReportGeneralResponse generalReports() {

        ReportGeneralResponse reports = new ReportGeneralResponse();

        reports.setBooks((int) bookRepository.count());
        reports.setAuthors((int) authorRepository.count());
        reports.setPublishers((int) publisherRepository.count());
        reports.setCategories((int) categoryRepository.count());
        reports.setLoans((int) loanRepository.count());
        reports.setUnReturnedBooks(loanRepository.unreturnedBookNumbers());
        reports.setExpiredBooks(loanRepository.expiredBookNumbers());
        reports.setMembers((int) userRepository.count());


        return reports;

    }

    public List mostPopularBooks(Integer amount, Pageable pageable) {
       List<Object[]> popularBooks = loanRepository.mostPopularBooks(amount, pageable);
       //TODO pageable.stream yaparak limitleyebiliyoruz.

        return popularBooks;
    }

    public Page<ReportBookResponse> unReturnedBooks(Pageable pageable) {

        Page<ReportBookResponse> unreturnedBooks = loanRepository.unReturnedBooks(pageable);
        if(unreturnedBooks == null) throw new ResourceNotFoundException(String.format(ErrorMessage.UNRETURNED_BOOK_NOT_FOUND_MESSAGE));
        return unreturnedBooks;
    }

    public Page<ReportBookResponse> expiredBooks(Pageable pageable) {
        LocalDateTime today = LocalDateTime.now();

        Page<ReportBookResponse> expiredBooks = loanRepository.expiredBooks(today,pageable);
        if(expiredBooks == null) throw new ResourceNotFoundException(String.format(ErrorMessage.EXPIRED_BOOK_NOT_FOUND_MESSAGE));
        return expiredBooks;
    }

    public List<Object[]> mostBorrowers(Pageable pageable) {
        List<Object[]> mostBorrowers = loanRepository.mostBorrowers(pageable);

        return mostBorrowers;
    }
}
