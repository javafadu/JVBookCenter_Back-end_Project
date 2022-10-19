package com.library.dto;


import com.library.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PublisherDTO {


    private Long id;
    @NotNull(message = "Please provide Publisher Name")
    @Size(min=2,max=50,message="Publisher Name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;


    public PublisherDTO(Publisher publisher) {
        this.id = publisher.getId();
        this.name =publisher.getName();
    }
}
