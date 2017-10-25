package com.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Queue;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.domain.Book;
import com.domain.BookDto;
import com.mongodb.DBObject;
import com.repository.BookRepository;

@RestController
@RequestMapping("book")
public class BookController {

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private JmsMessagingTemplate jmsMessagingTemplate;

	@Autowired
	private Queue queue;
	
	private Map<String,List<DBObject>> resultMap=Collections.synchronizedMap(new HashMap<>());
	
	@RequestMapping(value = "/list/{key}", method = RequestMethod.GET)
	List<DBObject> getBookList(@PathVariable String key) {
		jmsMessagingTemplate.convertAndSend(queue, key);
		while(true){
			
			if(resultMap!=null && resultMap.containsKey(key)){
				return resultMap.get(key);
			}else{
				String str= create(key);
				if("没有数据".equals(str)){
					return null;
				}
			}
		}
		
	}
	
	/**
	 * 监听 book:querytask队列
	 * 
	 * @param msg
	 */
	@JmsListener(destination = "book:querytask",containerFactory="booktaskFactory")
	@Transactional
	public String create(String key) {
		List<DBObject> list =bookRepository.findByKey(key);
		if (list != null && list.size() > 0) {
			if(resultMap!=null){
				if(!resultMap.containsKey("")){
					resultMap.put(key, list);
					return "有数据";
				}
			}else{
				resultMap.put(key, list);
			}
			
			
		}else{
			System.out.println("没有数据");
			return "没有数据";
		}
		return key;
		
	}

	@RequestMapping(value = "save", method = RequestMethod.GET)
	@ResponseBody
	public String sendMessage( String json) {
		Book book=JSONObject.parseObject(json, Book.class);
		mongoTemplate.save(book);
		return "保存成功";
	}

}
