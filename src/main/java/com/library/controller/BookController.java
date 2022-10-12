package com.library.controller;


import com.library.dto.request.BookRequest;
import com.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;


    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> createBook(@Valid @RequestBody BookRequest bookRequest)  {

        bookService.saveBook(bookRequest);

        Map<String,String> map=new HashMap<>();
        map.put("message", "Book Successfully Created");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }






}
