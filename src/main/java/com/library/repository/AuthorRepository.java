package com.library.repository;

import com.library.domain.Author;
import com.library.dto.AuthorDTO;
import com.library.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

@Query("SELECT new com.library.dto.AuthorDTO(author) FROM Author author")
    Page<AuthorDTO> findAllAuthorsWithPage(Pageable pageable);


@Query("SELECT new com.library.dto.AuthorDTO(a)  FROM Author a where  lower(a.name) like %?1% ")
    Page<AuthorDTO> getAllAuthersWithQAdmin(String q, Pageable pageable);

}
