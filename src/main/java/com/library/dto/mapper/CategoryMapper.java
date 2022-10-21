package com.library.dto.mapper;


import com.library.domain.Author;
import com.library.domain.Category;
import com.library.dto.AuthorDTO;
import com.library.dto.CategoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDTO categoryToCategoryDTO(Category category);
}
