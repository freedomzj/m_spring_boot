package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by zengjie on 17/6/20.
 */
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
//@ComponentScan("com.*")   //相对于package根目录而言 不需要此扫描
@MapperScan("com.mapper.SqlMapper")
public class MyApp{

    public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

}
