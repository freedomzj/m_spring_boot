package com.config;

import com.domain.User;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMessage;
import org.springframework.http.converter.HttpMessageConverter;

/**
 * Created by zengjie on 17/6/20.
 */
@Configurable
public class MyConfiguration {

    //上下文中存在的任何HttpMessageConverter bean将被添加到转换器列表中。您也可以以这种方式覆盖默认转换器。
    @Bean
    public HttpMessageConverters customConverters() {
       CustomJsonHttpMessageConverter customJsonHttpMessageConverter=new CustomJsonHttpMessageConverter();
        return new HttpMessageConverters(customJsonHttpMessageConverter);
    }
}

