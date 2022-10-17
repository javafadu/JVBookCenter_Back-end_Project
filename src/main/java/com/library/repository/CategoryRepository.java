package com.library.repository;

import com.library.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT max(c.sequence) FROM tbl_categories c",
            nativeQuery = true)
    Integer findMaxSequence();

}
