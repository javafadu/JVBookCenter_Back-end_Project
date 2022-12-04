package com.library.service;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.domain.Category;
import com.library.domain.ImageFile;
import com.library.dto.BookDTO;
import com.library.dto.mapper.BookMapper;
import com.library.dto.request.BookRegisterRequest;
import com.library.dto.response.BookResponse;
import com.library.exception.BadRequestException;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private ImageFileRepository imageFileRepository;


    public BookResponse saveBook(BookRegisterRequest bookRegisterRequest, String imageId) {

        ImageFile imFile=    imageFileRepository.findById(imageId).
                orElseThrow(()->new ResourceNotFoundException
                        (String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));

        Book book = new Book();
        Set<ImageFile> imFiles=new HashSet<>();
        imFiles.add(imFile);


        book.setName(bookRegisterRequest.getName());
        book.setIsbn(bookRegisterRequest.getIsbn());
        book.setPageCount(bookRegisterRequest.getPageCount());
        book.setPublishDate(bookRegisterRequest.getPublishDate());

        Author authorOfBook = authorRepository.findById(bookRegisterRequest.getBookAuthor()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookRegisterRequest.getBookAuthor())));

        book.setBookAuthor(authorOfBook);
        book.setBookPublisher(publisherService.getPublisherById(bookRegisterRequest.getBookPublisher()));

        Category categoryOfBook = categoryRepository.findById(bookRegisterRequest.getBookCategory()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookRegisterRequest.getBookAuthor())));

        book.setBookCategory(categoryOfBook);


        book.setImage(imFiles);
        book.setLoanable(true);
        book.setShelfCode(bookRegisterRequest.getShelfCode());
        book.setActive(true);
        book.setFeatured(bookRegisterRequest.getFeatured());
        LocalDateTime today = LocalDateTime.now();
        book.setCreateDate(today);
        book.setBuiltIn(false);

        bookRepository.save(book);




        BookResponse bookRegisterResponse = bookMapper.bookToBookResponse(book);

        return bookRegisterResponse;

    }






    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
    }

    @Transactional
    public BookResponse findBookById(Long id){
        Book book =bookRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        BookResponse bookResponse = bookMapper.bookToBookResponse(book);
        return bookResponse;

    }

    @Transactional
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



    @Transactional
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
        } else {
            booksPage=bookRepository.getAllBooksWithPageByAdmin(pageable);
        }

        return booksPage;

    }


    @Transactional
    public BookResponse updateBook(Long id, BookDTO bookDTO, String imageId){
        // check the id if exists or not. If it is not exist throw exception
        // if exist, get it by id and assign to foundBook
        Book foundBook =bookRepository.findById(id).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        // check the imageId if exists or not. If it is not exist throw exception
        // if exist, get it by id and assign to imFile
        ImageFile imFile=imageFileRepository.findById(imageId).
                orElseThrow(()-> new ResourceNotFoundException (String.format(ErrorMessage.IMAGE_NOT_FOUND_MESSAGE, imageId)));

        Set<ImageFile> imgs= foundBook.getImage();
        imgs.add(imFile);

        Book book=new Book();
        book.setId(foundBook.getId());
        book.setName(bookDTO.getName());
        book.setIsbn(bookDTO.getIsbn());
        book.setPageCount(bookDTO.getPageCount());

        Author authorOfBook = authorRepository.findById(bookDTO.getBookAuthor()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookDTO.getBookAuthor())));

        book.setBookAuthor(authorOfBook);
        book.setBookPublisher(publisherService.getPublisherById(bookDTO.getBookPublisher()));
        book.setPublishDate(bookDTO.getPublishDate());

        Category categoryOfBook = categoryRepository.findById(bookDTO.getBookCategory()).orElseThrow(()-> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,bookDTO.getBookAuthor())));

        book.setBookCategory(categoryOfBook);
        book.setImage(imgs);
        book.setLoanable(bookDTO.getLoanable());
        book.setShelfCode(bookDTO.getShelfCode());
        book.setActive(bookDTO.getActive());
        book.setFeatured(bookDTO.getFeatured());

        if (bookDTO.getCreateDate() != null) {
            book.setCreateDate(bookDTO.getCreateDate());
        } else {
            book.setCreateDate(foundBook.getCreateDate());
        }
        book.setBuiltIn(bookDTO.getBuiltIn());

        bookRepository.save(book);


        BookResponse bookResponse = bookMapper.bookToBookResponse(book);

        return bookResponse;

    }




    @Transactional
    public BookResponse deleteBookById(Long id){

        Book book =getBookById(id);

        BookResponse bookResponse= bookMapper.bookToBookResponse(book);

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

    @Transactional
    public Page<BookResponse> getFeaturedBooks(Pageable pageable){

       Page<BookResponse> featuredBooks = bookRepository.findAllFeaturedIsTrue(pageable);
       if(featuredBooks==null) throw new ResourceNotFoundException(String.format(ErrorMessage.NO_FEATURED_BOOK_MESSAGE));

       return featuredBooks;

    }

}
