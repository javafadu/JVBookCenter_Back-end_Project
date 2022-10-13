package com.library.service;

import com.library.domain.Book;
import com.library.dto.request.BookRegisterRequest;
import com.library.dto.response.BookRegisterResponse;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BookService {


    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private AuthorService authorService;
    private PublisherService publisherService;
    private CategoryService categoryService;


    public BookRegisterResponse saveBook(BookRegisterRequest bookRegisterRequest) {


        Book book = new Book();


        book.setName(bookRegisterRequest.getName());
        book.setIsbn(bookRegisterRequest.getIsbn());
        book.setPageCount(bookRegisterRequest.getPageCount());
        book.setPublishDate(bookRegisterRequest.getPublishDate());

        book.setBookAuthor(authorService.getAuthorById(bookRegisterRequest.getBookAuthor()));
        book.setBookPublisher(publisherService.getPublisherById(bookRegisterRequest.getBookPublisher()));
        book.setBookCategory(categoryService.getCategoryById(bookRegisterRequest.getBookCategory()));

        book.setLoanable(true);
        book.setShelfCode(bookRegisterRequest.getShelfCode());
        book.setActive(true);
        book.setFeatured(bookRegisterRequest.getFeatured());
        LocalDateTime today = LocalDateTime.now();
        book.setCreateDate(today);
        book.setBuiltIn(false);

        bookRepository.save(book);


        BookRegisterResponse bookRegisterResponse = new BookRegisterResponse();

        bookRegisterResponse.setName(bookRegisterRequest.getName());
        bookRegisterResponse.setIsbn(bookRegisterRequest.getIsbn());
        bookRegisterResponse.setPageCount(bookRegisterRequest.getPageCount());
        bookRegisterResponse.setBookAuthor(bookRegisterRequest.getBookAuthor());
        bookRegisterResponse.setBookPublisher(bookRegisterRequest.getBookPublisher());
        bookRegisterResponse.setPublishDate(bookRegisterRequest.getPublishDate());
        bookRegisterResponse.setBookCategory(bookRegisterRequest.getBookCategory());
        bookRegisterResponse.setImage(bookRegisterRequest.getImage());
        bookRegisterResponse.setLoanable(true);
        bookRegisterResponse.setShelfCode(bookRegisterRequest.getShelfCode());
        bookRegisterResponse.setActive(true);
        bookRegisterResponse.setFeatured(bookRegisterRequest.getFeatured());
        bookRegisterResponse.setCreateDate(today);
        bookRegisterResponse.setBuiltIn(false);


        return bookRegisterResponse;

    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException("Not found"));
    }


}
