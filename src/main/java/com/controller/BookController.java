package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.Book;
import com.repository.BookRepository;

@RestController
@RequestMapping("book")
public class BookController {
	
	@Autowired
	private BookRepository bookRepository;
	
	
	 @RequestMapping(value="/list/{key}", method=RequestMethod.GET)
	    List<Book> getBookList(@PathVariable String key) {
	        return bookRepository.findByKey(key);
	    }

}
