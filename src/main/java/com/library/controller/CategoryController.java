package com.library.controller;


import com.library.dto.CategoryDTO;
import com.library.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoryController {

    CategoryService categoryService;



    @PostMapping("/add")
    //TO DO: PreAuthorize()admin eklenecek
    public ResponseEntity<String> saveCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        categoryService.saveCategory(categoryDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
