package com.library.controller;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.dto.BookDTO;
import com.library.dto.request.BookRequest;
import com.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;
    byte[] bookImageResponse;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> createBook(@Valid @RequestBody BookRequest bookRequest)  {


        bookService.saveBook(bookRequest, bookImageResponse);

        Map<String,String> map=new HashMap<>();
        map.put("message", "Book Successfully Created");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }






}
