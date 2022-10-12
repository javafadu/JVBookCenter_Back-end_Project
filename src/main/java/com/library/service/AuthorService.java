package com.library.service;

import com.library.domain.Author;
import com.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@AllArgsConstructor
@Service
public class AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    public Author getAuthorById(Long id) {

        return authorRepository.findById(id).orElseThrow(null);

    }

}
