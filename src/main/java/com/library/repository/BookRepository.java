package com.library.repository;

import com.library.domain.Book;
import com.library.dto.response.BookRegisterResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(book)  FROM Book book")
    Page<BookRegisterResponse> findAllBookWithPage(Pageable pageable);

    // Get All Books with parameters:

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where b.active=TRUE and (b.name like %?1% OR b.bookAuthor.name like %?1% OR b.isbn like %?1% or b.bookPublisher.name like %?1%)")
    Page<BookRegisterResponse> getAllBooksWithQ(String q, Pageable pageable);

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where b.active=TRUE and b.bookCategory.id=?1")
    Page<BookRegisterResponse> getAllBooksWithCat(Long cat, Pageable pageable);

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where b.active=TRUE and b.bookAuthor.id=?1")
    Page<BookRegisterResponse> getAllBooksWithAuthor(Long author, Pageable pageable);

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where b.active=TRUE and b.bookPublisher.id=?1")
    Page<BookRegisterResponse> getAllBooksWithPublisher(Long publisher, Pageable pageable);

    // active true and false for admin

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where  b.name like %?1% OR b.bookAuthor.name like %?1% OR b.isbn like %?1% or b.bookPublisher.name like %?1%")
    Page<BookRegisterResponse> getAllBooksWithQAdmin(String q, Pageable pageable);

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where  b.bookCategory.id=?1")
    Page<BookRegisterResponse> getAllBooksWithCatAdmin(Long cat, Pageable pageable);

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where  b.bookAuthor.id=?1")
    Page<BookRegisterResponse> getAllBooksWithAuthorAdmin(Long author, Pageable pageable);

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(b)  FROM Book b where  b.bookPublisher.id=?1")
    Page<BookRegisterResponse> getAllBooksWithPublisherAdmin(Long publisher, Pageable pageable);


   }
