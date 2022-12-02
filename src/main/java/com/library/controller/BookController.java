package com.library.controller;


import com.library.dto.BookDTO;
import com.library.dto.request.BookRegisterRequest;


import com.library.dto.response.BookResponse;
import com.library.exception.BadRequestException;
import com.library.exception.message.ErrorMessage;
import com.library.service.BookService;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 1- CREATE a Book
    // endpoint: [{server_url}/books/add/{imageId}
    /* Json body:
    {
    "name": "Animal Farm",
    "isbn": "678-22-22655-55-9",
    "pageCount": 112,
    "bookAuthor": 4,
    "bookPublisher": 4,
    "publishDate": 1945,
    "bookCategory": 4,
    "shelfCode": "PL-001",
    "featured": false
    }
     */
    @PostMapping("/add/{imageId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> createBook(@PathVariable String imageId, @Valid @RequestBody BookRegisterRequest bookRegisterRequest) {

        BookResponse bookRegisterResponse = bookService.saveBook(bookRegisterRequest, imageId);


        return new ResponseEntity<>(bookRegisterResponse, HttpStatus.CREATED);

    }

    // 2- GET a Book with Id
    // endpoint: [{server_url}/books/41
    @GetMapping("/{id}")

    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        BookResponse bookResponse = bookService.findBookById(id);


        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);

    }

    // 3- GET filtered Books with Paging by anyone (only active)
    // endpoint: [{server_url}/books
    @GetMapping()
    public ResponseEntity<Page<BookResponse>> getBooksWithPage(

            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "cat", defaultValue = "") Long cat,
            @RequestParam(required = false, value = "author", defaultValue = "") Long author,
            @RequestParam(required = false, value = "publisher", defaultValue = "") Long publisher,

            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "5") int size,
            @RequestParam(required = false, value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false, value = "direction", defaultValue = "ASC") Sort.Direction direction) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        String qLower = q.toLowerCase();

        if (q.isEmpty() && cat == null && author == null && publisher == null) {
            throw new BadRequestException(String.format(ErrorMessage.GET_ALL_BOOKS_PARAMETERS_NULL_MESSAGE));
        }
        Page<BookResponse> books = bookService.findAllWithPage(qLower, cat, author, publisher, pageable);

        return ResponseEntity.ok(books);
    }


    // 4- GET filtered Books with Paging by Admin (active and passive)
    // endpoint: [{server_url}/books
    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<Page<BookResponse>> getBooksWithPageAdmin(

            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "cat", defaultValue = "") Long cat,
            @RequestParam(required = false, value = "author", defaultValue = "") Long author,
            @RequestParam(required = false, value = "publisher", defaultValue = "") Long publisher,

            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "5") int size,
            @RequestParam(required = false, value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false, value = "direction", defaultValue = "ASC") Sort.Direction direction) {


        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));


        String qLower = q.toLowerCase();

        Page<BookResponse> books = bookService.findAllWithPageAdmin(qLower, cat, author, publisher, pageable);

        return ResponseEntity.ok(books);
    }

    // 5- UPDATE a Book with id and body
    // endpoint: [{server_url}/books/{id}
    /* Json body:

    {
    "name": " Broken Barriers",
    "isbn": "123-44-22655-88-2",
    "pageCount": 114,
    "bookAuthor": 10,
    "bookPublisher": 6,
    "publishDate": 1938,
    "bookCategory": 14,
    "imageLink" : "BrokenBarriersNove.jpg",
    "loanable": true,
    "shelfCode": "RD-011",
    "active": true,
    "featured": false,
    "createDate":"10/10/2022 12:12:12",
    "builtIn": false
    }


     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<BookResponse> updateBook(@PathVariable Long id,
                                                   @RequestParam("imageId") String imageId,
                                                   @RequestBody BookDTO bookDTO) {

        BookResponse bookResponse = bookService.updateBook(id, bookDTO, imageId);


        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);

    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> deleteBook(@PathVariable Long id) {
        BookResponse bookResponse = bookService.deleteBookById(id);

        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }


    @GetMapping("/featured-books")
    public ResponseEntity<Page<BookResponse>> getBooksWithPageAdmin(
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "10") int size,
            @RequestParam(required = false, value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false, value = "direction", defaultValue = "ASC") Sort.Direction direction) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));

        Page<BookResponse> featuredBooks = bookService.getFeaturedBooks(pageable);

        return  ResponseEntity.ok(featuredBooks);

    }

}
