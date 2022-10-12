package com.library.categorytest;

import com.library.dto.CategoryDTO;
import com.library.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class saveCategoryTest {

 @Autowired
 CategoryService categoryService;



 @Test
 public void saveCategoryT(){
  CategoryDTO categoryDTO = new CategoryDTO();
  categoryDTO.setName("Selami");
  categoryDTO.setBuiltIn(true);
  categoryService.saveCategory(categoryDTO);

 }


}
