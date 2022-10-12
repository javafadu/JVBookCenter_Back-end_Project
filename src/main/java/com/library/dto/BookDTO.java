package com.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.domain.Author;
import com.library.domain.Category;
import com.library.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {


    private Long id;

    @Size(min = 2,max = 80,message="Size is exceeded")
    @NotNull(message = "Please provide book name")
    private String name;

    @NotNull(message = "Please provide isbn")
    @Size(min = 17, max = 17)
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",
            message = "Please provide valid isbn number")
    private String isbn;


    private Integer pageCount;

    @NotNull(message = "Please provide bookAuthor")
    private Author bookAuthor;

    @NotNull(message = "Please provide bookPublisher")
    private Publisher bookPublisher;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "yyyy", timezone = "Turkey")
    @NotNull(message = "Please provide publishDate")
    private Integer publishDate;

    @NotNull(message = "Please provide bookCategory")
    private Category bookCategory;


    //notnull
    private Boolean loanable = true;


    @NotNull(message = "Please provide shelfCode")
    @Size(min = 6, max = 6)
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$",
            message = "Please provide a shelfCode")
    private String shelfCode;

    //notnull
    private Boolean active = true;

    //notnull
    private Boolean featured = false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please provide createDate")
    private LocalDateTime createDate;

    //notnull
    private Boolean builtIn =false;

    //TODO
    private File image;

    //TODO
    private List<Loan> loanedBooks=new ArrayList<>();






}
