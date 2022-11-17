package com.library.service;

import com.library.domain.Category;
import com.library.domain.Publisher;
import com.library.dto.CategoryDTO;
import com.library.dto.PublisherDTO;
import com.library.dto.mapper.PublisherMapper;
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
    PublisherMapper publisherMapper;

    public PublisherDTO savePublisher(PublisherDTO publisherDTO){
        Publisher publisher=new Publisher();
        publisher.setName(publisherDTO.getName());
        publisher.setBuiltIn(false);
        publisherRepository.save(publisher);
        return publisherMapper.publisherToPublisherDTO(publisher);
    }

    public Publisher getPublisherById(Long id) {
        return publisherRepository.findById(id).orElseThrow(()->new RuntimeException(String.format(ErrorMessage.PUBLISHER_NOT_FOUND_MESSAGE,id)));
    }



    public Page<PublisherDTO> getPublisherWithPages(String q, Pageable pageable){

        Page<PublisherDTO> publisherWithPage = null;

        if (!q.isEmpty()) {
            publisherWithPage = publisherRepository.getAllPublishersWithQAdmin(q,pageable);
        } else {
            publisherWithPage = publisherRepository.getPublishersWithPage(pageable);
        }



        if(publisherWithPage.isEmpty()) throw new ResourceNotFoundException(String.format(ErrorMessage.NO_DATA_IN_DB_TABLE_MESSAGE,"Publishers"));

        return publisherWithPage;

    }

    public PublisherDTO getPublisherWithId(Long id){
        PublisherDTO responsePublisherWithId=publisherRepository.getPublisherwithId(id);
        if(responsePublisherWithId ==null ) throw new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id));
        return responsePublisherWithId;

    }


    public PublisherDTO deletePublisherWithId(Long id) {
        // get publisher with id and check it it is exist or not
        Publisher deletingPublisher = publisherRepository.findById(id).orElseThrow(() -> new RuntimeException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));
        // check Does the publisher have any registered books?
        if (!deletingPublisher.getPublisherBooks().isEmpty()) {
            throw new RuntimeException(String.format(ErrorMessage.PUBLISHER_HAS_RELATED_RECORDS_MESSAGE));
        }
        // check the publisher is builtIn or not
        if(deletingPublisher.getBuiltIn()) throw new RuntimeException(String.format(ErrorMessage.BUILTIN_DELETE_ERROR_MESSAGE,id));

        publisherRepository.deleteById(deletingPublisher.getId());
        return publisherMapper.publisherToPublisherDTO(deletingPublisher);
    }



    public PublisherDTO updatePublisher(Long id, PublisherDTO publisherDTO) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(()->new ResourceNotFoundException(String.format(ErrorMessage.RESOURCE_NOT_FOUND_MESSAGE,id)));

        publisher.setId(id);
        publisher.setName(publisherDTO.getName());
        publisher.setBuiltIn(publisherDTO.getBuiltIn());

        publisherRepository.save(publisher);



        return publisherMapper.publisherToPublisherDTO(publisher);

    }


}
