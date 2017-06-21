package com.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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

   /* 跨原始资源共享（CORS）是大多数浏览器实现的W3C规范，允许您以灵活的方式指定什么样的跨域请求被授权，而不是使用一些不太安全和不太强大的方法，如IFRAME或JSONP。
    从版本4.2起，Spring MVC支持CORS开箱即用。使用控制器方法在Spring Boot应用程序中使用
    @CrossOrigin注释的CORS配置不需要任何特定的配置。可以通过使用定制的addCorsMappings（CorsRegistry）方法注册WebMvcConfigurer bean来定义全局CORS配置：*/
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }
        };
    }

    @Bean
    public FreeMarkerView viewConfigurer(){
        return new MyFreeMarkerView();
    }

    class MyFreeMarkerView extends  FreeMarkerView{
        private static final String CONTEXT_PATH = "base";

        @Override
        protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
            model.put(CONTEXT_PATH, request.getContextPath());
            super.exposeHelpers(model, request);
        }
    }

    /* @Bean
   public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(8084);
        factory.setSessionTimeout(10, TimeUnit.MINUTES);
        factory.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404.html"));
        return factory;
    }*/
}

