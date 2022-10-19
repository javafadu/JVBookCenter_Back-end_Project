package com.library.service;

import com.library.domain.Publisher;
import com.library.domain.User;
import com.library.dto.PublisherDTO;

import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PublisherService {

    PublisherRepository publisherRepository;

    public Publisher savePublisher(PublisherDTO publisherDTO){
        Publisher publisher=new Publisher();
        publisher.setName(publisherDTO.getName());
        publisher.setBuiltIn(false);
        publisherRepository.save(publisher);
        return publisher;
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(()->new RuntimeException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE,id)));
    }



    public Page<PublisherDTO> getPublisherWithPages(Pageable pageable){

        Page<PublisherDTO> publisherWithPage = publisherRepository.getPublishersWithPage(pageable);

        if(publisherWithPage.isEmpty()) throw new ResourceNotFoundException("Not found");

        return publisherWithPage;

    }

    public PublisherDTO getPublisherWithId(Long id){
        PublisherDTO responsePublisherWithId=publisherRepository.getPublisherwithId(id);

        return responsePublisherWithId;

    }



    public PublisherDTO deletePublisherWithId(Long id){
        PublisherDTO responsePublisherDelete=publisherRepository.deletePublisherWithById(id);

        return responsePublisherDelete;

    }

}
