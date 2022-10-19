package com.library.service;

import com.library.dto.request.MostPopularBooksRequest;
import com.library.dto.response.MostPopularBooksReponse;
import com.library.dto.response.ReportGeneralResponse;
import com.library.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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

        return popularBooks;
    }
}
