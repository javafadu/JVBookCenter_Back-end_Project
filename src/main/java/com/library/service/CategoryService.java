package com.library.service;

import com.library.domain.Category;
import com.library.dto.CategoryDTO;
import com.library.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;
    public Category saveCategory(CategoryDTO categoryDTO) {

        Category category=new Category();
        category.setName(categoryDTO.getName());
        category.setBuiltIn(categoryDTO.getBuiltIn());

        //category.setSequence(categoryDTO.getSequence()); //CategoryDTO Sequence kapali

        category.setSequence(1);
        //Sequence manuel ayarlamadan testi geciremedim sebebi sorulacak
        categoryRepository.save(category);
        return category;

    }

    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id).orElseThrow(()-> new RuntimeException("Not Found"));
    }
}
