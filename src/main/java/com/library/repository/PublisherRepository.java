package com.library.repository;

import com.library.domain.Publisher;
import com.library.dto.PublisherDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {



    @Query("SELECT new com.library.dto.PublisherDTO(p) FROM Publisher p ")
    Page<PublisherDTO> getPublishersWithPage(Pageable pageable);


    @Query(" SELECT new com.library.dto.PublisherDTO(p) FROM Publisher p WHERE p.id=?1 ")
    PublisherDTO getPublisherwithId(Long id);


    @Query(" DELETE FROM Publisher p WHERE p.id =?1 ")
    PublisherDTO deletePublisherWithById(Long id);
}
