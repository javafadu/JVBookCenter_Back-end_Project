package com.library.controller;


import com.library.domain.Book;
import com.library.dto.BookDTO;
import com.library.dto.request.BookRegisterRequest;
import com.library.dto.response.BookRegisterResponse;

import com.library.dto.response.BookUpdateResponse;
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


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;


    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookRegisterResponse> createBook(@Valid @RequestBody BookRegisterRequest bookRegisterRequest)  {

        BookRegisterResponse bookRegisterResponse = bookService.saveBook(bookRegisterRequest);


        return new ResponseEntity<>(bookRegisterResponse, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")

    public ResponseEntity<BookRegisterResponse> getBookById(@PathVariable Long id) {
        BookRegisterResponse bookResponse = bookService.findBookById(id);


        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);

    }

    @GetMapping()
    public ResponseEntity<Page<BookRegisterResponse>> getBooksWithPage (

                                                                        @RequestParam(required = false, value = "q", defaultValue = "") String q,
                                                                        @RequestParam(required = false, value = "cat", defaultValue = "") Long cat,
                                                                        @RequestParam(required = false, value = "author", defaultValue = "") Long author,
                                                                        @RequestParam(required = false, value = "publisher", defaultValue = "") Long publisher,

                                                                             @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(required = false,value = "size", defaultValue = "5") int size,
                                                                             @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                                             @RequestParam(required = false,value = "direction", defaultValue = "ASC") Sort.Direction direction) {



        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        if(q.isEmpty() && cat==null && author==null && publisher==null)
        {
            throw new BadRequestException(String.format(ErrorMessage.GET_ALL_BOOKS_PARAMETERS_NULL_MESSAGE));
        }
             Page<BookRegisterResponse> books = bookService.findAllWithPage(q,cat,author,publisher,pageable);

        return ResponseEntity.ok(books);
    }



    @GetMapping("/pages")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<BookRegisterResponse>> getBooksWithPageAdmin (

            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "cat", defaultValue = "") Long cat,
            @RequestParam(required = false, value = "author", defaultValue = "") Long author,
            @RequestParam(required = false, value = "publisher", defaultValue = "") Long publisher,

            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false,value = "size", defaultValue = "5") int size,
            @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false,value = "direction", defaultValue = "ASC") Sort.Direction direction) {



        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        if(q.isEmpty() && cat==null && author==null && publisher==null)
        {
            throw new BadRequestException(String.format(ErrorMessage.GET_ALL_BOOKS_PARAMETERS_NULL_MESSAGE));
        }
        Page<BookRegisterResponse> books = bookService.findAllWithPageAdmin(q,cat,author,publisher,pageable);

        return ResponseEntity.ok(books);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<BookUpdateResponse> updateBook(@PathVariable("id") Long id,
                                                         @RequestBody BookDTO bookDTO) {

        BookUpdateResponse bookDTOResponse = bookService.updateBook(id,bookDTO);


        return new ResponseEntity<>(bookDTOResponse, HttpStatus.CREATED);

    }


        @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookRegisterResponse> deleteBook(@PathVariable Long id){
        BookRegisterResponse bookResponse = bookService.deleteBookById(id);

        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }



}
