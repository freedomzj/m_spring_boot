package com;

import com.domain.City;
import org.apache.activemq.command.ActiveMQQueue;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
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
//@EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)//看说明,可以知道,是因为@Autowired在注入时,是匹配类型的,如果存在两个一个类型的实例时就会报错，报错信息指出，
// 一个redisTemplate是在我的RedisConfig.class中配置的,另一个是RedisAutoConfiguration中进行声明的,因此两种解决方案:
// 在启动类处加上注解:@EnableAutoConfiguration(exclude = RedisAutoConfiguration.class)关闭自动注入配置;
//在RedisUtil.class注入时结合@Qualifier("")注解:

public class MyApp{

    //对应纯注解模式下 mybatis 不需要 @bean MyBatisConfig 与 @bean SessionFactoryConfig
     public static void main(String[] args) {
        SpringApplication.run(MyApp.class, args);
    }

    @Bean
    public Queue queue() {
        return new ActiveMQQueue("book:querytask");
    }
    

}
