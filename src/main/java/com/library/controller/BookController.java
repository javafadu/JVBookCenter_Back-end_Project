package com.library.controller;


import com.library.dto.BookDTO;
import com.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping("/add")
    public ResponseEntity<Map<String, String>> createBook(@Valid
                                                              @RequestParam("name") BookDTO bookDTO,
                                                          @RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            byte[] bytes = file.getBytes();
        }

        Map<String,String> map=new HashMap<>();
        map.put("message", "Book Successfully Created");
        map.put("status","true");

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }






}
