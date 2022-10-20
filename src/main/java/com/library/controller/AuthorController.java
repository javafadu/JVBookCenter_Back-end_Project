package com.library.controller;


import com.library.domain.Author;
import com.library.dto.AuthorDTO;
import com.library.dto.CategoryDTO;
import com.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    AuthorService authorService;



    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Author> saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {

        return new ResponseEntity<>(authorService.saveAuthor(authorDTO),HttpStatus.CREATED);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(Author author){
       Author authorResponse=authorService.getAuthorById(author.getId());

        return ResponseEntity.ok(authorResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> updateAuthoryById(@Valid @RequestBody AuthorDTO authorDTO) {

        return ResponseEntity.ok(authorService.updateAuthorWithId(authorDTO));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> getAllAuthorsByPage(@RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                               @RequestParam(required = false,value = "size", defaultValue = "20") int size,
                                                               @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                               @RequestParam(required = false,value = "type", defaultValue = "ASC") Sort.Direction direction
    ){
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        Page<AuthorDTO> authorDTOPage = authorService.getAuthorPage(pageable);
        return ResponseEntity.ok(authorDTOPage);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Author> deleteAuthoryById(@PathVariable Long id ){
        Author deletedAuthor=authorService.deleteAuthorById(id);

        return ResponseEntity.ok(deletedAuthor);
    }

}
