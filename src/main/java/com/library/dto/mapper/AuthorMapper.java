package com.library.dto.mapper;

import com.library.domain.Author;
import com.library.domain.Book;
import com.library.dto.AuthorDTO;
import com.library.dto.response.BookRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    AuthorDTO authorToAuthorDTO(Author author);

}
