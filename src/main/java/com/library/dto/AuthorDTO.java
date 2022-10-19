package com.library.dto;

import com.library.domain.Author;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {

    private Long id;
    @NotNull(message = "Please provide Author Name")
    @Size(min = 4, max = 70, message = "Author Name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    public AuthorDTO(Author author){
        this.name = author.getName();
        this.id = author.getId();
    }

}
