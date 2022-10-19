package com.library.controller;

import com.library.dto.request.MostPopularBooksRequest;
import com.library.dto.response.LoanAuthResponseWithBook;
import com.library.dto.response.MostPopularBooksReponse;
import com.library.dto.response.ReportGeneralResponse;
import com.library.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/reports")
@AllArgsConstructor
public class ReportController {

    private ReportService reportService;

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<ReportGeneralResponse> getAuthLoansWithPage () {

        ReportGeneralResponse generalReports = reportService.generalReports();
        return ResponseEntity.ok(generalReports);
    }

    @GetMapping("/most-popular-books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<MostPopularBooksReponse>> mostPopularBooks (
            @RequestParam(required = true) Integer amount,
            @RequestParam(required = false) Integer page,
                    @RequestParam(required = false) Integer size
    ) {

        Pageable pageable = PageRequest.of(page,size);


        List<MostPopularBooksReponse> mostPopularBooks = reportService.mostPopularBooks(amount, pageable);
        return ResponseEntity.ok(mostPopularBooks);
    }
}
