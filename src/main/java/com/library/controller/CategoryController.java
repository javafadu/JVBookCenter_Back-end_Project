package com.library.controller;


import com.library.domain.Category;
import com.library.dto.CategoryDTO;
import com.library.service.CategoryService;
import lombok.AllArgsConstructor;
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



    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

        return new ResponseEntity<>(categoryService.saveCategory(categoryDTO),HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(Long id){
        Category categoryResponse=categoryService.getCategoryById(id);

        return ResponseEntity.ok(categoryResponse);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategoryById(Long id ){
        Category deletedCategory=categoryService.deleteCategoryById(id);

        return ResponseEntity.ok(deletedCategory);
    }




}
