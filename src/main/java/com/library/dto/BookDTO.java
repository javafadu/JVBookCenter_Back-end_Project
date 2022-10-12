package com.library.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.library.domain.Author;
import com.library.domain.Category;
import com.library.domain.Loan;
import com.library.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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


    @Size(min = 2,max = 80,message="Size is exceeded")
    @NotNull(message = "Please provide book name")
    private String name;

    @NotNull(message = "Please provide isbn")
    @Size(max = 17)
    @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{5}-\\d{2}-\\d$",
            message = "Please provide valid isbn number")
    private String isbn;


    private Integer pageCount;

    @NotNull(message = "Please provide a Author id")
    private Author bookAuthor;

    @NotNull(message = "Please provide a Publichser id")
    private Publisher bookPublisher;


    private Integer publishDate;

    @NotNull(message = "Please provide A Category id")
    private Category bookCategory;

    private File image;



    @NotNull(message = "Please provide shelf Code")
    @Size(max = 6)
    @Pattern(regexp = "^[A-Z]{2}-\\d{3}$",
            message = "Please provide a valid shelf Code")
    private String shelfCode;

    @NotNull(message = "Please provide shelf Code")
    private Boolean featured = false;



    // BELOW variables are not requested from client
    // they will have default value or starter value

    @NotNull(message = "Please provide active value true or false")
    private Boolean active = true;

    @NotNull(message = "Please provide builtIn value true or false")
    private Boolean builtIn =false;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @NotNull(message = "Please provide createDate")
    private LocalDateTime createDate;

    @NotNull(message = "Please provide loanable value true or false")
    private Boolean loanable = true;


    // The missing variables

    /*
        private Long id;
        private List<Loan> loanedBooks=new ArrayList<>();

     */






}
