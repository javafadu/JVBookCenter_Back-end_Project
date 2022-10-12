package com.library.service;

import com.library.dto.BookDTO;
import com.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {

    @Autowired
    private BookRepository bookRepository;


    //deneme icin manuel bir Long tipinde veri olusturmaya calistim
    Long  imageId = 1l;


    public  void saveBook(BookDTO bookDTO){

        //imageId gerekecek, image kisminida yapmamiz gerek :(
        //bookRepository.save();


    }




}
