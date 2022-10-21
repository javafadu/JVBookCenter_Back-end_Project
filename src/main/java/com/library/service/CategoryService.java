package com.library.service;

import com.library.domain.Category;
import com.library.dto.CategoryDTO;
import com.library.dto.mapper.CategoryMapper;
import com.library.exception.message.ErrorMessage;
import com.library.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class CategoryService {

    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryDTO saveCategory(CategoryDTO categoryDTO) {

        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setBuiltIn(false);

        Integer sequenceNumber = 0;
        if (categoryRepository.count() != 0) {
            sequenceNumber = categoryRepository.findMaxSequence();
        }

        category.setSequence(sequenceNumber + 1);
        categoryRepository.save(category);

        return categoryMapper.categoryToCategoryDTO(category);

    }

    public Category getCategoryById(Long id) {

        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(
                String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));
    }

    public Page<CategoryDTO> getCategoryPage(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        Page<CategoryDTO> dtoPage = categories.map(new Function<Category, CategoryDTO>() {

            @Override
            public CategoryDTO apply(Category category) {
                return categoryMapper.categoryToCategoryDTO(category);
            }
        });
        return dtoPage;
    }

    public Category deleteCategoryById(Long id) {

        Category deletingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE)));
        if (deletingCategory.getCategoryBooks().isEmpty()) {
            categoryRepository.deleteById(deletingCategory.getId());
            return deletingCategory;
        } else throw new RuntimeException(String.format(ErrorMessage.CATEGORY_HAS_RELATED_RECORDS_MESSAGE));
    }

    @Transactional
    public CategoryDTO updateCategoryWithId(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId()).orElseThrow(()->new RuntimeException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE)));
        category.setName(categoryDTO.getName());
        categoryRepository.save(category);
        categoryDTO.setSequence(category.getSequence());
        return categoryDTO;
    }
}
