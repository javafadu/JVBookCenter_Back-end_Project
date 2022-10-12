package com.library.service;

import com.library.domain.Author;
import com.library.dto.AuthorDTO;
import com.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {

    AuthorRepository authorRepository;

    public Author saveAuthor(AuthorDTO authorDTO){
        Author author=new Author();
        author.setName(authorDTO.getName());
        author.setBuiltIn(authorDTO.getBuiltIn());
        authorRepository.save(author);
        return author;

    }

    public Author getAuthorById(Long id) {

        return authorRepository.findById(id).orElseThrow(()-> new RuntimeException("No Found"));

    }
}