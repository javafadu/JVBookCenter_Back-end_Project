package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.User;
import com.library.dto.BookDTO;
import com.library.dto.request.BookRequest;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.CategoryRepository;
import com.library.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {


    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private AuthorService authorService;
    private PublisherService publisherService;
    private CategoryService categoryService;


    public Book saveBook(BookRequest bookRequest, byte[] bookImageResponse) {


        Book book = new Book();

        book.setName(bookRequest.getName());
        book.setIsbn(bookRequest.getIsbn());
        book.setPageCount(bookRequest.getPageCount());
        book.setPublishDate(bookRequest.getPublishDate());

        book.setBookAuthor(authorService.getAuthorById(bookRequest.getBookAuthor()));
        book.setBookPublisher(publisherService.getPublisherById(bookRequest.getBookPublisher()));
        book.setBookCategory(categoryService.getCategoryById(bookRequest.getBookCategory()));

        book.setLoanable(true);
        book.setShelfCode(bookRequest.getShelfCode());
        book.setActive(true);
        book.setFeatured(bookRequest.getFeatured());

        LocalDateTime today = LocalDateTime.now();

        book.setCreateDate(today);

        book.setBuiltIn(false);


        bookRepository.save(book);

        return book;

    }


}
