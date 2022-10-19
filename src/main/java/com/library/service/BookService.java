package com.library.service;

import com.library.domain.Book;
import com.library.dto.BookDTO;
import com.library.dto.mapper.BookMapper;
import com.library.dto.mapper.UserMapper;
import com.library.dto.request.BookRegisterRequest;
import com.library.dto.response.BookRegisterResponse;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;
import com.library.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class BookService {


    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private AuthorService authorService;
    private PublisherService publisherService;
    private CategoryService categoryService;
    private LoanRepository loanRepository;
    private BookMapper bookMapper;


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
        book.setImageLink(bookRegisterRequest.getImageLink());

        bookRepository.save(book);


        BookRegisterResponse bookRegisterResponse = new BookRegisterResponse();

        bookRegisterResponse.setName(bookRegisterRequest.getName());
        bookRegisterResponse.setIsbn(bookRegisterRequest.getIsbn());
        bookRegisterResponse.setPageCount(bookRegisterRequest.getPageCount());
        bookRegisterResponse.setBookAuthor(authorService.getAuthorById(bookRegisterRequest.getBookAuthor()));
        bookRegisterResponse.setBookPublisher(publisherService.getPublisherById(bookRegisterRequest.getBookPublisher()));
        bookRegisterResponse.setPublishDate(bookRegisterRequest.getPublishDate());
        bookRegisterResponse.setBookCategory(categoryService.getCategoryById(bookRegisterRequest.getBookCategory()));
        bookRegisterResponse.setImageLink("images/books/" + bookRegisterResponse.getBookCategory().getId() + "/" + bookRegisterRequest.getImageLink());
        bookRegisterResponse.setLoanable(true);
        bookRegisterResponse.setShelfCode(bookRegisterRequest.getShelfCode());
        bookRegisterResponse.setActive(true);
        bookRegisterResponse.setFeatured(bookRegisterRequest.getFeatured());
        bookRegisterResponse.setCreateDate(today);
        bookRegisterResponse.setBuiltIn(false);


        return bookRegisterResponse;

    }


    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
    }


    public BookRegisterResponse findBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        BookRegisterResponse bookResponse = bookMapper.BookToBookRegisterResponse(book);
        return bookResponse;

    }

    public Page<BookRegisterResponse> findAllWithPage(Pageable pageable) {

        return bookRepository.findAllBookWithPage(pageable);
    }


    @Transactional
    public Book updateBook(Long id, BookDTO bookDTO) {

        Book foundBook = bookRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));
        if (foundBook.getBuiltIn()) {
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Book book = new Book();
        book.setId(foundBook.getId());
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setPageCount(bookDTO.getPageCount());
        book.setBookAuthor(authorService.getAuthorById(bookDTO.getBookAuthor()));
        book.setBookPublisher(publisherService.getPublisherById(bookDTO.getBookPublisher()));
        book.setPublishDate(bookDTO.getPublishDate());
        book.setBookCategory(categoryService.getCategoryById(bookDTO.getBookCategory()));
        book.setImageLink("images/books/" + bookDTO.getBookCategory() + "/" + bookDTO.getImageLink());
        book.setLoanable(foundBook.getLoanable());
        book.setShelfCode(bookDTO.getShelfCode());
        book.setActive(bookDTO.getActive());
        book.setFeatured(bookDTO.getFeatured());
        book.setCreateDate(foundBook.getCreateDate());
        book.setBuiltIn(foundBook.getBuiltIn());


        bookRepository.save(book);
        return book;

    }


    public BookRegisterResponse deleteBookById(Long id) {

        Book book = getBookById(id);

        BookRegisterResponse bookRegisterResponse = bookMapper.BookToBookRegisterResponse(book);

        boolean exists = loanRepository.existsByLoanedBooks(book);
        if (exists) {
            throw new BadRequestException(ErrorMessage.BOOK_USED_BY_RESERVATION_MESSAGE);
        }

        bookRepository.delete(book);
        return bookRegisterResponse;
    }

    public void updateBookLoanable(Long id) {

        Book bookUpdated = getBookById(id);
        bookUpdated.setLoanable(!bookUpdated.getLoanable());
        bookRepository.save(bookUpdated);

    }


}
