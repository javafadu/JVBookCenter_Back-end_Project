package com.library.controller;


import com.library.domain.Author;
import com.library.domain.Publisher;
import com.library.dto.PublisherDTO;
import com.library.dto.response.LoanAuthPagesResponse;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
@AllArgsConstructor
public class PublisherController {

    PublisherService publisherService;




    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Publisher> savePublisher(@Valid @RequestBody PublisherDTO publisherDTO) {


        return new ResponseEntity<>(publisherService.savePublisher(publisherDTO), HttpStatus.CREATED);

    }


    @GetMapping()
    public ResponseEntity<Page<PublisherDTO>> getPublishersWithPage (
                                                                             @RequestParam(required = false, value = "page", defaultValue = "0") int page,
                                                                             @RequestParam(required = false,value = "size", defaultValue = "3") int size,
                                                                             @RequestParam(required = false,value = "sort", defaultValue = "name") String prop,
                                                                             @RequestParam(required = false,value = "direction", defaultValue = "DESC") Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page,size,Sort.by(direction,prop));

        Page<PublisherDTO> publisherDTO =  publisherService.getPublisherWithPages(pageable);

        return ResponseEntity.ok(publisherDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublisherDTO> getPublisherWithId(@PathVariable Long id){
        PublisherDTO publishersWithId=publisherService.getPublisherWithId(id);

        return ResponseEntity.ok(publishersWithId);
    }

    @DeleteMapping("/admin/{id}/auth")
    public ResponseEntity<PublisherDTO> deletePublisher(@PathVariable Long id){
        PublisherDTO deletePublisher=publisherService.deletePublisherWithId(id);

        return ResponseEntity.ok(deletePublisher);
    }

}
