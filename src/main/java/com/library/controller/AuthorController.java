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


    // 1- CREATE an AUTHOR
    // endpoint: [{server_url}/authors
    /* Json body:
    {
    "name": "Dan Brown"
    }
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {

        return new ResponseEntity<>(authorService.saveAuthor(authorDTO),HttpStatus.CREATED);

    }

    // 2- GET an AUTHOR with Id
    // endpoint: [{server_url}/authors/{id}
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable Long id){
       AuthorDTO authorResponse=authorService.getAuthorById(id);

        return ResponseEntity.ok(authorResponse);
    }

    // 3- UPDATE an AUTHOR with Id (@Path) and author info (json body)
    // endpoint: [{server_url}/authors/{id}
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> updateAuthoryById(@Valid @PathVariable Long id, @RequestBody AuthorDTO authorDTO) {

        return ResponseEntity.ok(authorService.updateAuthorWithId(id,authorDTO));
    }

    // 4- Get all Athors with paging
    // endpoint: [{server_url}/authors
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

    // 5- DELETE a LOAN with an id
    // endpoint: [{server_url}/authors/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> deleteAuthoryById(@PathVariable Long id ){
        AuthorDTO deletedAuthor=authorService.deleteAuthorById(id);

        return ResponseEntity.ok(deletedAuthor);
    }

}
