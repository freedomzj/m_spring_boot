package com.controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zengjie on 17/6/20.
 */
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
public class MyApp{

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

}
