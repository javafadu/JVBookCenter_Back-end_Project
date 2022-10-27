package com.library.controller;

import com.library.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/public")
@AllArgsConstructor
public class PublicReportsController {

    private ReportService reportService;

    // PUBLIC REPORTS - PermitAll
    @GetMapping("/top-books")
    public ResponseEntity<List<Object[]>> topBooks (
            @RequestParam(required = false, value = "top", defaultValue = "5") int top
    ) {

        List<Object[]> topBooks = reportService.topBooks(top);
        return ResponseEntity.ok(topBooks);
    }

    @GetMapping("/top-categories")
    public ResponseEntity<List<Object[]>> topCategories (
            @RequestParam(required = false, value = "top", defaultValue = "5") int top
    ) {

        List<Object[]> topCategories = reportService.topCategories(top);
        return ResponseEntity.ok(topCategories);
    }

    @GetMapping("/top-publishers")
    public ResponseEntity<List<Object[]>> topPublishers (
            @RequestParam(required = false, value = "top", defaultValue = "5") int top
    ) {

        List<Object[]> topPublishers = reportService.topPubishers(top);
        return ResponseEntity.ok(topPublishers);
    }

}
