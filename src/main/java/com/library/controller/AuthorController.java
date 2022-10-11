package com.library.controller;


import com.library.dto.AuthorDTO;
import com.library.service.AuthorService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/authors")
@AllArgsConstructor
public class AuthorController {

    AuthorService authorService;



    @PostMapping("/add")
    //TO DO: PreAuthorize()admin eklenecek
    public ResponseEntity<String> saveAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        authorService.saveAuthor(authorDTO);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }
}
