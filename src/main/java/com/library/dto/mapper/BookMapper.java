package com.library.dto.mapper;


import com.library.domain.Book;
import com.library.dto.BookDTO;
import com.library.dto.response.BookRegisterResponse;
import com.library.dto.response.BookUpdateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {



    BookUpdateResponse BookToBookUpdateResponse(Book book);

    BookRegisterResponse BookToBookRegisterResponse(Book book);




}