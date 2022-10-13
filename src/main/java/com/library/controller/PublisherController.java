package com.library.controller;


import com.library.domain.Publisher;
import com.library.dto.PublisherDTO;
import com.library.service.PublisherService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/publishers")
@AllArgsConstructor
public class PublisherController {

    PublisherService publisherService;



    @PostMapping("/add")
    //TO DO: PreAuthorize()admin eklenecek
    public ResponseEntity<Publisher> savePublisher(@Valid @RequestBody PublisherDTO publisherDTO) {


        return new ResponseEntity<>(publisherService.savePublisher(publisherDTO), HttpStatus.CREATED);

    }



}
