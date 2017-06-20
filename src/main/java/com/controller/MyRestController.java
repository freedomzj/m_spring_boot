package com.controller;

import com.domain.Customer;
import com.domain.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengjie on 17/6/20.
 */
@RestController
@RequestMapping(value="/user")
public class MyRestController {

    @RequestMapping(value="/{userId}", method= RequestMethod.GET)
    public User getUser(@PathVariable Long userId) {
        User user=new User();
        user.setId(userId);
        user.setPassword("123456");
        user.setUsername("zengjie");
        return user;
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

    @RequestMapping(value="/{userId}", method=RequestMethod.DELETE)
    public User deleteUser(@PathVariable Long userId) {
        User user=new User();
        user.setId(userId);
        return user;
    }

    @RequestMapping(value = "test")
    public String jsonTest(){
        return "大家好啊我是json字符串";
    }

}
