package com.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.domain.Book;
import com.domain.Explore;

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
	public List<Book> findByKey(String key){
    	final Query query =new Query();
		Criteria criteria =new Criteria();
//		if(key!=null && key.length()==10){
			criteria.and("key").is(key);
//			query.skip(curPage-1);
//			query.limit(pageSize);
//		}else{
//			criteria.and("explore").regex(key);
//			query.limit(1000);
//		}
//	     criteria.wshere(key);
//	     criteria.and("key").alike(sample)
		query.addCriteria(criteria);
//		query.maxScan(1000);
		List<Book> list= mongoTemplate.find(query,Book.class);
		return list;
	}

}
