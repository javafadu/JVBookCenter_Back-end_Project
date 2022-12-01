package com.library;


import com.library.domain.Author;
import com.library.domain.Publisher;
import com.library.domain.User;
import com.library.repository.AuthorRepository;
import com.library.repository.PublisherRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class saveAndFindByTest {



    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    @Test
    public void whenAuthorSaveAndFindByIdTest() {
        Author author = new Author();
        author.setBuiltIn(false);
        author.setName("Yazar");

        authorRepository.save(author);
            String name = authorRepository.findAll().get(0).getName();
            assertThat(name).isEqualTo("Yazar");

    }

    @Test
    public void whenPublisherSaveAndFindByIdTest() {
        Publisher publisher = new Publisher();
        publisher.setName("Yayinevi");
        publisher.setBuiltIn(false);

        publisherRepository.save(publisher);
        String name = publisherRepository.findAll().get(0).getName();
        assertThat(name).isEqualTo("Yayinevi");

    }


}
