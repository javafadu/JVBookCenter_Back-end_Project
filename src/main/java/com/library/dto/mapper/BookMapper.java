package com.library.dto.mapper;


import com.library.domain.Book;
import com.library.dto.response.BookRegisterResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    BookRegisterResponse BookToBookRegisterResponse(Book book);




}
