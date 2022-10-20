package com.library.repository;

import com.library.domain.Book;
import com.library.dto.response.BookRegisterResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT new com.library.dto.response.BookRegisterResponse(book)  FROM Book book")
    Page<BookRegisterResponse> findAllBookWithPage(Pageable pageable);


}
