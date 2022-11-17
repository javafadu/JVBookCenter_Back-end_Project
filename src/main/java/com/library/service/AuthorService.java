package com.library.service;

import com.library.domain.Author;
import com.library.domain.Category;
import com.library.dto.AuthorDTO;
import com.library.dto.CategoryDTO;
import com.library.dto.mapper.AuthorMapper;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.AuthorRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@AllArgsConstructor
public class AuthorService {

    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setBuiltIn(false);

        authorRepository.save(author);
        return authorMapper.authorToAuthorDTO(author);

    }

    public AuthorDTO getAuthorById(Long id) {

        Author author = authorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE, id)));

        return authorMapper.authorToAuthorDTO(author);
    }


    public AuthorDTO deleteAuthorById(Long id) {
        // get author with id and check it it is exist or not
        Author deletingAuthor = authorRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE, id)));
        // check Does the author have any registered books?
        if (!deletingAuthor.getAuthorBooks().isEmpty()) {
            throw new RuntimeException(String.format(ErrorMessage.AUTHOR_HAS_RELATED_RECORDS_MESSAGE));
        }
        // check the author is builtIn or not
        if(deletingAuthor.getBuiltIn()) throw new RuntimeException(String.format(ErrorMessage.BUILTIN_DELETE_ERROR_MESSAGE,id));

        authorRepository.deleteById(deletingAuthor.getId());
        return authorMapper.authorToAuthorDTO(deletingAuthor);
    }

    public Page<AuthorDTO> getAuthorPage(String q, Pageable pageable) {

        Page<AuthorDTO> authors = null;

        if (!q.isEmpty()) {
            authors = authorRepository.getAllAuthersWithQAdmin(q, pageable);
        } else {
            authors = authorRepository.findAllAuthorsWithPage(pageable);
        }

        if(authors.isEmpty()) throw new ResourceNotFoundException(String.format(ErrorMessage.NO_DATA_IN_DB_TABLE_MESSAGE,"Authors"));



        return authors;
    }

    public AuthorDTO updateAuthorWithId(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id).orElseThrow(()->new RuntimeException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE)));
        author.setId(id);
        author.setName(authorDTO.getName());
        author.setBuiltIn(authorDTO.getBuiltIn());
        authorRepository.save(author);
        authorDTO.setId(id);
        return authorDTO;
    }
}
