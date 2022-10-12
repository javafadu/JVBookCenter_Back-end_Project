package com.library.service;

import com.library.domain.Author;
import com.library.domain.Category;
import com.library.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryService {

    private CategoryRepository categoryRepository;

    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));

    }

    public void createCategory(String name) {

        Category category = new Category();
        category.setName(name);
        category.setSequence(categoryRepository.countDistinctBy()+1);
    }

}
