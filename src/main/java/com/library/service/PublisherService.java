package com.library.service;

import com.library.domain.Author;
import com.library.domain.Publisher;
import com.library.repository.AuthorRepository;
import com.library.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PublisherService {

    @Autowired
    private PublisherRepository publisherRepository;

    public Publisher getPublisherById(Long id) {

        return publisherRepository.findById(id).orElseThrow(null);

    }

}
