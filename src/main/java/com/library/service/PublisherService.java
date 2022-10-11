package com.library.service;

import com.library.domain.Publisher;
import com.library.dto.PublisherDTO;
import com.library.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublisherService {

    PublisherRepository publisherRepository;

    public void savePublisher(PublisherDTO publisherDTO){
        Publisher publisher=new Publisher();
        publisher.setName(publisherDTO.getName());
        publisher.setBuiltIn(publisherDTO.getBuiltIn());
        publisherRepository.save(publisher);

    }

}
