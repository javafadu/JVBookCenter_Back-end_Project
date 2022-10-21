package com.library.dto;

import com.library.domain.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryDTO {

    Long id;

    @NotNull(message="Please Provide Category Name")
    @Size(min=2,max=80,message="Category Name '${validatedValue}' must be between {min} and {max} chars long")
    private String name;

    private Boolean builtIn;

    private Integer sequence;

    public CategoryDTO(Category category){
        this.name = category.getName();
        this.id=category.getId();
        this.sequence = category.getSequence();
        this.builtIn=category.getBuiltIn();
    }


}
