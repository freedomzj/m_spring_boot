package com;

import com.domain.City;
import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

/**
 * Created by zengjie on 17/6/20.
 */
@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
//@ComponentScan("com.*")   //相对于package根目录而言 不需要此扫描
@MapperScan("com.mapper")
@EnableJms
@EntityScan(basePackageClasses=City.class)
public class MyApp{

    //对应纯注解模式下 mybatis 不需要 @bean MyBatisConfig 与 @bean SessionFactoryConfig
     public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("sample.queue");
    }

}
