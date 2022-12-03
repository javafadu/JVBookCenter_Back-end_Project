package com.library.dto.mapper;

import com.library.domain.Book;
import com.library.domain.ImageFile;
import com.library.dto.BookDTO;
import com.library.dto.response.BookResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target="image", ignore=true)
    Book bookResponseToBook(BookResponse bookResponse);

    @Mapping(source="image", target="image", qualifiedByName="getImageAsString")
    BookResponse bookToBookResponse(Book book);

    @Named("getImageAsString")
    public static Set<String> getImageId(Set<ImageFile> images){
        Set<String> imgs=new HashSet<>();
        imgs=images.stream().map(image->image.getId().toString()).collect(Collectors.toSet());

        return imgs;
    }



}
