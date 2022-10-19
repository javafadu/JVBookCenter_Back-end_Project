package com.library.service;

import com.library.domain.Author;
import com.library.dto.AuthorDTO;
import com.library.exception.message.ErrorMessage;
import com.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthorService {

    AuthorRepository authorRepository;

    public Author saveAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setBuiltIn(false);

        authorRepository.save(author);
        return author;

    }

    public Author getAuthorById(Long id) {

        return authorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

    }


    public Author deleteAuthorById(Long id) {

        Author deletingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, id)));
        if (deletingAuthor.getAuthorBooks().isEmpty()) {
            authorRepository.deleteById(deletingAuthor.getId());
            return deletingAuthor;
        } else throw new RuntimeException(String.format(ErrorMessage.AUTHOR_HAS_RELATED_RECORDS_MESSAGE));
    }
}
