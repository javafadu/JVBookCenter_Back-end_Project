package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Category;
import com.library.dto.BookDTO;
import com.library.dto.mapper.BookMapper;
import com.library.dto.request.BookRegisterRequest;
import com.library.dto.response.BookResponse;
import com.library.exception.BadRequestException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.*;
import lombok.AllArgsConstructor;
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
    private UserRepository userRepository;
    private AuthorService authorService;
    private PublisherService publisherService;
    private CategoryService categoryService;
    private CategoryRepository categoryRepository;
    private LoanRepository loanRepository;
    private BookMapper bookMapper;


    public BookResponse saveBook(BookRegisterRequest bookRegisterRequest) {


        Book book = new Book();


        book.setName(bookRegisterRequest.getName());
        book.setIsbn(bookRegisterRequest.getIsbn());
        book.setPageCount(bookRegisterRequest.getPageCount());
        book.setPublishDate(bookRegisterRequest.getPublishDate());

        Author authorOfBook = authorRepository.findById(bookRegisterRequest.getBookAuthor()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookRegisterRequest.getBookAuthor())));

        book.setBookAuthor(authorOfBook);
        book.setBookPublisher(publisherService.getPublisherById(bookRegisterRequest.getBookPublisher()));

        Category categoryOfBook = categoryRepository.findById(bookRegisterRequest.getBookCategory()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookRegisterRequest.getBookAuthor())));

        book.setBookCategory(categoryOfBook);


        book.setImageLink("assets/img/books/"+bookRegisterRequest.getBookCategory()+"/"+bookRegisterRequest.getImageLink());
        book.setLoanable(true);
        book.setShelfCode(bookRegisterRequest.getShelfCode());
        book.setActive(true);
        book.setFeatured(bookRegisterRequest.getFeatured());
        LocalDateTime today = LocalDateTime.now();
        book.setCreateDate(today);
        book.setBuiltIn(false);

        bookRepository.save(book);




        BookResponse bookRegisterResponse = bookMapper.BookToBookResponse(book);

        return bookRegisterResponse;

    }






    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
    }


    public BookResponse findBookById(Long id){
        Book book =bookRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        BookResponse bookResponse = bookMapper.BookToBookResponse(book);
        return bookResponse;

    }

    public Page<BookResponse> findAllWithPage(String q, Long cat, Long author, Long publisher, Pageable pageable){

        Page<BookResponse> booksPage = null;
        if(!q.isEmpty()) {
            booksPage = bookRepository.getAllBooksWithQ(q,pageable);
        } else if (cat!=null) {
            booksPage = bookRepository.getAllBooksWithCat(cat,pageable);
        } else if (author!=null) {
            booksPage = bookRepository.getAllBooksWithAuthor(author,pageable);
        }
        else if (publisher!=null) {
            booksPage = bookRepository.getAllBooksWithPublisher(publisher,pageable);
        }

        return booksPage;

    }



    public Page<BookResponse> findAllWithPageAdmin(String q, Long cat, Long author, Long publisher, Pageable pageable){

        Page<BookResponse> booksPage = null;
        if(!q.isEmpty()) {
            booksPage = bookRepository.getAllBooksWithQAdmin(q,pageable);
        } else if (cat!=null) {
            booksPage = bookRepository.getAllBooksWithCatAdmin(cat,pageable);
        } else if (author!=null) {
            booksPage = bookRepository.getAllBooksWithAuthorAdmin(author,pageable);
        }
        else if (publisher!=null) {
            booksPage = bookRepository.getAllBooksWithPublisherAdmin(publisher,pageable);
        }

        return booksPage;

    }


    @Transactional
    public BookResponse updateBook(Long id, BookDTO bookDTO){

        Book foundBook =bookRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        if (foundBook.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        Book book=new Book();
        book.setId(foundBook.getId());
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setPageCount(bookDTO.getPageCount());

        Author authorOfBook = authorRepository.findById(bookDTO.getBookAuthor()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookDTO.getBookAuthor())));

        book.setBookAuthor(authorOfBook);
        book.setBookPublisher(publisherService.getPublisherById(bookDTO.getBookPublisher()));
        book.setPublishDate(bookDTO.getPublishDate());

        Category categoryOfBook = categoryRepository.findById(bookDTO.getBookAuthor()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookDTO.getBookAuthor())));

        book.setBookCategory(categoryOfBook);
        book.setImageLink("images/books/"+bookDTO.getBookCategory()+"/"+bookDTO.getImageLink());
        book.setLoanable(bookDTO.getLoanable());
        book.setShelfCode(bookDTO.getShelfCode());
        book.setActive(bookDTO.getActive());
        book.setFeatured(bookDTO.getFeatured());
        book.setCreateDate(bookDTO.getCreateDate());
        book.setBuiltIn(bookDTO.getBuiltIn());

        bookRepository.save(book);


        BookResponse bookResponse = bookMapper.BookToBookResponse(book);

        return bookResponse;

    }





    public BookResponse deleteBookById(Long id){

        Book book =getBookById(id);

        BookResponse bookResponse= bookMapper.BookToBookResponse(book);

        boolean exists=loanRepository.existsByLoanedBooks(book);
        if (exists){
            throw new BadRequestException(ErrorMessage.BOOK_USED_BY_RESERVATION_MESSAGE);
        }

        if (book.getBuiltIn()){
            throw new BadRequestException(ErrorMessage.NOT_PERMITTED_METHOD_MESSAGE);
        }

        bookRepository.delete(book);
        return bookResponse;
    }

    public void updateBookLoanable(Long id) {

        Book bookUpdated = getBookById(id);
        bookUpdated.setLoanable(!bookUpdated.getLoanable());
        bookRepository.save(bookUpdated);

    }

}
