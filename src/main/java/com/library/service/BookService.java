package com.library.service;

import com.library.dto.BookDTO;
import com.library.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BookService {


    private BookRepository bookRepository;




    public  void saveBook(BookDTO bookDTO){



    }




}
