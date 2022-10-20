package com.library.controller;


import com.library.domain.Category;
import com.library.dto.CategoryDTO;
import com.library.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.lang.reflect.Type;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    CategoryService categoryService;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        return new ResponseEntity<>(categoryService.saveCategory(categoryDTO), HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    //TODO : Login islevsel hale geldiginde antMatch yapilacak.
    //TODO : Response icin DAO olusturulacak
    public ResponseEntity<Category> getCategoryById(Long id) {
        Category categoryResponse = categoryService.getCategoryById(id);

        return ResponseEntity.ok(categoryResponse);
    }

    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAllCategoriesByPage(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                    @RequestParam(required = false, value = "size", defaultValue = "20") int size,
                                                                    @RequestParam(required = false, value = "sort", defaultValue = "name") String prop,
                                                                    @RequestParam(required = false, value = "direction", defaultValue = "ASC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        Page<CategoryDTO> categoryDTOPage = categoryService.getCategoryPage(pageable);
        return ResponseEntity.ok(categoryDTOPage);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategoryById(@Valid @RequestBody CategoryDTO categoryDTO) {

        return ResponseEntity.ok(categoryService.updateCategoryWithId(categoryDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategoryById(Long id) {
        Category deletedCategory = categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(deletedCategory);
    }


}
