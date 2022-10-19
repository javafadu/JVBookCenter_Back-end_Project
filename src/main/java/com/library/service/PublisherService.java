package com.library.service;

import com.library.domain.Publisher;
import com.library.dto.PublisherDTO;
import com.library.exception.ResourceNotFoundException;
import com.library.exception.message.ErrorMessage;
import com.library.repository.PublisherRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
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

    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(() -> new RuntimeException("Id not found"));
        if (publisher.getBuiltIn() == false) throw new IllegalArgumentException("Publisher is not in builtin");
        publisher.setId(id);
        publisher.setName(publisherDTO.getName());

        publisherRepository.save(publisher);

        PublisherDTO responsePublisherUpdated=new PublisherDTO();
        responsePublisherUpdated.setId(id);
        responsePublisherUpdated.setName(publisher.getName());


        return responsePublisherUpdated;

    }


}
