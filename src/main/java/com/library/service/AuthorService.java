package com.library.service;

import com.library.domain.Author;
import com.library.domain.Category;
import com.library.dto.AuthorDTO;
import com.library.dto.CategoryDTO;
import com.library.dto.mapper.AuthorMapper;
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
    public Page<AuthorDTO> getAuthorPage(Pageable pageable) {
        Page<AuthorDTO> authors = authorRepository.findAllAuthorsWithPage(pageable);
//        Page<AuthorDTO> dtoPage = authors.map(new Function<Author, AuthorDTO>() {
//
//            @Override
//            public AuthorDTO apply(Author author) {
//                return authorMapper.authorToAuthorDTO(author);
//            }
//        });
        return authors;
    }

    public AuthorDTO updateAuthorWithId(AuthorDTO authorDTO) {
        Author author = authorRepository.findById(authorDTO.getId()).orElseThrow(()->new RuntimeException(String.format(ErrorMessage.AUTHOR_NOT_FOUND_MESSAGE)));
        author.setName(authorDTO.getName());
        authorRepository.save(author);
        return authorDTO;
    }
}
