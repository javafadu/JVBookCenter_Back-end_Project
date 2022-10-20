package com.library.authortest;

import com.library.dto.AuthorDTO;
import com.library.service.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class saveAuthorTest {

 @Autowired
 AuthorService authorService;



 @Test
 public void saveAuthorT(){
  AuthorDTO authorDTO = new AuthorDTO();
  authorDTO.setName("Selami");
  authorService.saveAuthor(authorDTO);

 }


}
