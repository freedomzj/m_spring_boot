package com.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.domain.Book;

@Repository
public class BookRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insert(Book book){
		mongoTemplate.save(book);
	}
	
	public void insertList(List<Book> books){
		mongoTemplate.insertAll(books);
	}
	
	public void deleteI(Book book){
		//
		mongoTemplate.remove(book);
	}

}
