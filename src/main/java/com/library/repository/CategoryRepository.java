package com.library.repository;

import com.library.domain.Category;
import com.library.dto.AuthorDTO;
import com.library.dto.CategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(
            value = "SELECT max(c.sequence) FROM tbl_categories c",
            nativeQuery = true)
    Integer findMaxSequence();
    @Query("SELECT new com.library.dto.CategoryDTO(category) FROM Category category")
    Page<CategoryDTO> findAllCategoryWithPage(Pageable page);

}
