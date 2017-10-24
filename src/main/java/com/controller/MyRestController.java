package com.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.domain.City;
import com.domain.Customer;
import com.domain.Explore;
import com.domain.User;
import com.mapper.UserMapper;
import com.repository.CityRepository;

/**
 * Created by zengjie on 17/6/20.
 */
@RestController
@RequestMapping(value="/user")
public class MyRestController extends  BaseCtl {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CityRepository cityRepository;
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @RequestMapping(value="/{username}", method= RequestMethod.GET)
    public User getUser(@PathVariable String username) {
        Page<City> page= cityRepository.findAll(new PageRequest(0,2));
        City city=cityRepository.findName("北京");
        User user=userMapper.findByName(username);
        return user;
    }

    @RequestMapping(value="add/{username}/{password}",method = RequestMethod.GET)
    public String add(@PathVariable String username,@PathVariable String password){
        userMapper.insert(username,password);
        return "操作成功添加"+username;
    }


    @RequestMapping(value="/{userId}/customers", method=RequestMethod.GET)
    List<Customer> getUserCustomers(@PathVariable Long userId) {

        List<Customer> customers=new ArrayList<Customer>();
        Customer customer=new Customer();
        customer.setPostion("部门经理");
        customer.setUsername("构思者");
        customer.setUser(new User(userId,"zengjie","123456"));
        Customer customer1=new Customer();
        customer1.setPostion("部门主管");
        customer1.setUsername("异想天开者");
        customer1.setUser(new User(userId,"zengjie","123456"));
        customers.add(customer);
        customers.add(customer1);
        return customers;
    }

    @RequestMapping(value="/dep/{userId}", method=RequestMethod.GET)
    public User findUser(@PathVariable Long userId) {
        User user=userMapper.findDepartment(userId);
        return user;
    }

    @RequestMapping(value = "test")
    public String jsonTest(){
        return "大家好啊我是json字符串";
    }
    
    @RequestMapping( value="explore/list/{explore}", method=RequestMethod.GET)
    public List<Explore> list(@PathVariable String explore){
    	final Query query =new Query();
		Criteria criteria =new Criteria();
//		if(key!=null && key.length()==10){
			criteria.and("explore").is(explore);
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
		System.out.println(mongoTemplate.getDb().getCollection("explore"));
		List<Explore> list= mongoTemplate.find(query,Explore.class);
		return list;
    }

}
