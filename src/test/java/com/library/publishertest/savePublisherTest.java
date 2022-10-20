package com.library.publishertest;

import com.library.dto.PublisherDTO;
import com.library.service.PublisherService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class savePublisherTest {


    @Autowired
    PublisherService publisherService;




    @Test
void savePublisher() {
    PublisherDTO publisherDTO = new PublisherDTO();
    publisherDTO.setName("Selami");
    publisherService.savePublisher(publisherDTO);
}
}
