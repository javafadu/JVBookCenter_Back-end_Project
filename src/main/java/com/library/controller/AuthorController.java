package com.library.controller;


import com.library.domain.Author;
import com.library.dto.AuthorDTO;
import com.library.service.AuthorService;
import lombok.AllArgsConstructor;
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
    //TODO : Login islevsel hale geldiginde antMatch yapilacak.
    //TODO : Response icin DAO olusturulacak
    public ResponseEntity<Author> getAuthorById(Author author){
       Author authorResponse=authorService.getAuthorById(author.getId());

        return ResponseEntity.ok(authorResponse);
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Author> deleteAuthoryById(Long id ){
        Author deletedAuthor=authorService.deleteAuthorById(id);

        return ResponseEntity.ok(deletedAuthor);
    }

}
