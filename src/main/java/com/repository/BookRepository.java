package com.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.domain.Book;
import com.domain.BookDto;
import com.domain.Explore;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

@Repository
public class BookRepository {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public void insert(BookDto book){
		mongoTemplate.save(book);
	}
	
	public void insertList(List<BookDto> books){
		mongoTemplate.insertAll(books);
	}
	
	public void deleteI(BookDto book){
		//
		mongoTemplate.remove(book);
	}
	
	public List<BookDto> findByKeys(String[] keys){
		final Query query =new Query();
		Criteria criteria =new Criteria();
		List<Criteria> criterias=new  ArrayList<>();
		for (int i = 1; i < keys.length; i++) {
			if(keys[i]!=null && keys[i].equals("")){
				criterias.add(new Criteria().and("Key").is(keys[i]));
			}
//			criteria.orOperator(criterias.t);
		}
		query.addCriteria(criteria);
//		query.maxScan(1000);
		List<BookDto> list= mongoTemplate.find(query,BookDto.class);
		return list;
	}
	
	public List<DBObject> findByKey(String key){
    	/*final Query query =new Query();
		Criteria criteria =new Criteria();
		if(key!=null && key.length()==10){
			criteria.and("Key").is(key);
		criteria.orOperator(Criteria.where("Key").is(key),Criteria.where("_id").is(0));
			criteria.and("_id").is(0);
			criteria.orOperator(new Criteria().and("key").is(key));
			query.skip(curPage-1);
			query.limit(pageSize);
		}else{
			criteria.and("explore").regex(key);
			query.limit(1000);
		}
	     criteria.wshere(key);
	     criteria.and("key").alike(sample)
		query.addCriteria(criteria);
		query.maxScan(1000);
		System.out.println(query.toString());*/
		
		DBCollection coll =mongoTemplate.getCollection("book");
		DBObject dbObj = new BasicDBObject();
		dbObj.put("Key", key);
		
		
//		dbObj.put("_id", 0);
		DBCursor cursor = coll.find(dbObj).limit(10);
		if (cursor != null) {
			List<DBObject> list = new ArrayList<DBObject>();
			list = cursor.toArray();
			return list;
		}
//		System.out.println(mongoTemplate.getDb().collectionExists("book"));
//		List<BookDto> list= mongoTemplate.find(query,BookDto.class);
//		System.out.println(list);
//		return list;
		return null;
	}
	

}
