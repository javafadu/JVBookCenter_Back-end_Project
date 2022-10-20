package com.library.controller;

import com.library.dto.response.MostPopularBooksReponse;
import com.library.dto.response.ReportGeneralResponse;
import com.library.dto.response.ReportBookResponse;
import com.library.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/unreturned-books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<ReportBookResponse>> unReturnedBooks (
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "3") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "expireDate") String prop,
            @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction)
     {

         Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<ReportBookResponse> unReturnedBooks = reportService.unReturnedBooks(pageable);
        return ResponseEntity.ok(unReturnedBooks);
    }

    @GetMapping("/expired-books")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<ReportBookResponse>> expiredBooks (
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "3") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "expireDate") String prop,
            @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction)
    {

        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<ReportBookResponse> expiredBooks = reportService.expiredBooks(pageable);
        return ResponseEntity.ok(expiredBooks);
    }


    @GetMapping("/most-borrowers")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Object[]>> mostBorrowers (

            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size
    ) {

        Pageable pageable = PageRequest.of(page,size);


        List<Object[]> mostBorrowers = reportService.mostBorrowers(pageable);
        return ResponseEntity.ok(mostBorrowers);
    }

}
