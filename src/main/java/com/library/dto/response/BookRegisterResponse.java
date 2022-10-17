package com.library.dto.response;

import com.library.domain.Author;
import com.library.domain.Category;
import com.library.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.File;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookRegisterResponse {


    private String name;

    private String isbn;

    private Integer pageCount;

    private Author bookAuthor;

    private Publisher bookPublisher;

    private Integer publishDate;

    private Category bookCategory;

    private String imageLink;

    private Boolean loanable = true;

    private String shelfCode;

    private Boolean active = true;

    private Boolean featured = false;

    private LocalDateTime createDate;

    private Boolean builtIn =false;


}
