package com.library.controller;


import com.library.domain.Book;
import com.library.dto.request.BookRegisterRequest;
import com.library.dto.response.BookRegisterResponse;
import com.library.dto.response.LResponse;
import com.library.dto.response.ResponseMessages;
import com.library.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;


    @PostMapping("/add")
    public ResponseEntity<BookRegisterResponse> createBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest)  {

        BookRegisterResponse book = bookService.saveBook(bookRegisterRequest);


        return new ResponseEntity<>(book, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookRegisterResponse> deleteBook(@PathVariable Long id){
        BookRegisterResponse bookResponse = bookService.deleteBookById(id);

        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }







}
