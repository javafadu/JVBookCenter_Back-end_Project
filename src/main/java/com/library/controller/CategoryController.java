package com.library.controller;



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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    CategoryService categoryService;

    // 1- CREATE a Category
    // endpoint: [{server_url}/categories
    /* Json body:
    {
    "name": "Horror"
    }
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        return new ResponseEntity<>(categoryService.saveCategory(categoryDTO), HttpStatus.CREATED);

    }

    // 2- Get a Category with Id
    // endpoint: [{server_url}/categories/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id) {
        CategoryDTO categoryResponse = categoryService.getCategoryById(id);
        return ResponseEntity.ok(categoryResponse);
    }

    // 4- Get all Categories with paging
    // endpoint: [{server_url}/categories
    @GetMapping
    public ResponseEntity<Page<CategoryDTO>> getAllCategoriesByPage(
            @RequestParam(required = false, value = "q", defaultValue = "") String q,
            @RequestParam(required = false, value = "page", defaultValue = "0") int page,
            @RequestParam(required = false, value = "size", defaultValue = "20") int size,
            @RequestParam(required = false, value = "sort", defaultValue = "name") String prop,
            @RequestParam(required = false, value = "direction", defaultValue = "ASC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, prop));
        String qLower = q.toLowerCase();
        Page<CategoryDTO> categoryDTOPage = categoryService.getCategoryPage(qLower, pageable);
        return ResponseEntity.ok(categoryDTOPage);
    }

    // 5- Update a category
    // endpoint: [{server_url}/categories/{id}
        /* Json body:
    {
    "name": "Horror",
    "builtIn": "false"
    }
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> updateCategoryById(@Valid @PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(categoryService.updateCategoryWithId(id,categoryDTO));
    }

    // 6- Delete a category
    // endpoint: [{server_url}/categories/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTO> deleteCategoryById(@PathVariable Long id) {
        CategoryDTO deletedCategory = categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(deletedCategory);
    }


}
