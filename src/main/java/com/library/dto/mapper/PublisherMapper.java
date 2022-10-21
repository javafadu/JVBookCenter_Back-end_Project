package com.library.dto.mapper;


import com.library.domain.Category;
import com.library.domain.Publisher;
import com.library.dto.CategoryDTO;
import com.library.dto.PublisherDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {

    PublisherDTO publisherToPublisherDTO(Publisher publisher);
}
