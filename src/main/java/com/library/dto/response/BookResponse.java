package com.library.dto.response;

import com.library.domain.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {

    private Long id;
    private String name;
    private String isbn;
    private Integer pageCount;
    private Author bookAuthor;
    private Publisher bookPublisher;
    private Integer publishDate;
    private Category bookCategory;
    private Set<String> image;
    private Boolean loanable = true;
    private String shelfCode;
    private Boolean active = true;
    private Boolean featured = false;
    private LocalDateTime createDate;
    private Boolean builtIn = false;
    // private List<Loan> loanedBooks = new ArrayList<>();

    public BookResponse(Book book){

        this.id=book.getId();
        this.name=book.getName();
        this.isbn=book.getIsbn();
        this.pageCount=book.getPageCount();
        this.bookAuthor=book.getBookAuthor();
        this.bookPublisher=book.getBookPublisher();
        this.publishDate=book.getPublishDate();
        this.bookCategory=book.getBookCategory();
        this.image=getImageId(book.getImage());
        this.loanable=book.getLoanable();
        this.shelfCode=book.getShelfCode();
        this.active=book.getActive();
        this.featured=book.getFeatured();
        this.createDate=book.getCreateDate();
        this.builtIn=book.getBuiltIn();


    }

    public Set<String> getImageId(Set<ImageFile> images){
        Set<String> imgStrSet=new HashSet<>();
        imgStrSet=images.stream().map(image->image.getId().toString()).collect(Collectors.toSet());
        return imgStrSet;
    }


}
