package com.library.dto.mapper;


import com.library.domain.Book;

import com.library.dto.response.BookResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {



   // @Mapping(target="loanedBooks", ignore = true)
    BookResponse BookToBookResponse(Book book);




}