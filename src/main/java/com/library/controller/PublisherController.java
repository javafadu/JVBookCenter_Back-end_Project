package com.library.controller;



import com.library.dto.PublisherDTO;
import com.library.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
@AllArgsConstructor
public class PublisherController {

    PublisherService publisherService;

    // 1- Create a Publisher
    // endpoint: [{server_url}/publishers
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublisherDTO> savePublisher(@Valid @RequestBody PublisherDTO publisherDTO) {

        return new ResponseEntity<>(publisherService.savePublisher(publisherDTO), HttpStatus.CREATED);

    }

    // 2- Get all Publishers with paging
    // endpoint: [{server_url}/publishers
    @GetMapping()
    public ResponseEntity<Page<PublisherDTO>> getPublishersWithPage (
            @RequestParam(required = false, value = "q", defaultValue = "") String q,
                                                                             @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                             @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                                             @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));
        String qLower = q.toLowerCase();
        Page<PublisherDTO> publisherDTO =  publisherService.getPublisherWithPages(qLower, pageable);

        return ResponseEntity.ok(publisherDTO);
    }

    // 3- Get a Publisher with Id
    // endpoint: [{server_url}/publishers/{id}
    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherWithId(@PathVariable Long id){
        PublisherDTO publishersWithId=publisherService.getPublisherWithId(id);

        return ResponseEntity.ok(publishersWithId);
    }

    // 4- Delete a Publisher with Id
    // endpoint: [{server_url}/publishers/{id}
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublisherDTO> deletePublisher(@PathVariable Long id){
        PublisherDTO deletePublisher=publisherService.deletePublisherWithId(id);

        return ResponseEntity.ok(deletePublisher);
    }

    // 5- Update a publisher
    // endpoint: [{server_url}/publishers/{id}
        /* Json body:
    {
    "name": "IsBankasi Yayinlari",
    "builtIn": "true"
    }
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PublisherDTO> updatePublisher(@PathVariable Long id,@RequestBody PublisherDTO publisherDTO){
        PublisherDTO updatePublisher=publisherService.updatePublisher(id,publisherDTO);

        return ResponseEntity.ok(updatePublisher);

    }


}
