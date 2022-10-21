package com.library.service;

import com.library.domain.Author;
import com.library.domain.Category;
import com.library.dto.AuthorDTO;
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

    public CategoryDTO getCategoryById(Long id) {

        Category category =  categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(
                String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE, id)));
        return categoryMapper.categoryToCategoryDTO(category);
    }

    public Page<CategoryDTO> getCategoryPage(Pageable pageable) {
        Page<CategoryDTO> categories = categoryRepository.findAllCategoryWithPage(pageable);
        return categories;
    }

/*    public Page<CategoryDTO> getCategoryPage(Pageable pageable) {
        Page<Category> categories = categoryRepository.findAll(pageable);
        Page<CategoryDTO> dtoPage = categories.map(new Function<Category, CategoryDTO>() {

            @Override
            public CategoryDTO apply(Category category) {
                return categoryMapper.categoryToCategoryDTO(category);
            }
        });
        return dtoPage;
    }*/

    public CategoryDTO deleteCategoryById(Long id) {
        // get category with id and check it it is exist or not
        Category deletingCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, id)));
        // check Does the category have any registered books?
        if (!deletingCategory.getCategoryBooks().isEmpty()) {
            throw new RuntimeException(String.format(ErrorMessage.AUTHOR_HAS_RELATED_RECORDS_MESSAGE));
        }
        // check the category is builtIn or not
        if(deletingCategory.getBuiltIn()) throw new RuntimeException(String.format(ErrorMessage.BUILTIN_DELETE_ERROR_MESSAGE,id));

        categoryRepository.deleteById(deletingCategory.getId());
        return categoryMapper.categoryToCategoryDTO(deletingCategory);
    }


    @Transactional
    public CategoryDTO updateCategoryWithId(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id).orElseThrow(()->new RuntimeException(String.format(ErrorMessage.CATEGORY_NOT_FOUND_MESSAGE)));
        category.setName(categoryDTO.getName());
        category.setBuiltIn(categoryDTO.getBuiltIn());
        categoryRepository.save(category);

        return categoryMapper.categoryToCategoryDTO(category);
    }
}
