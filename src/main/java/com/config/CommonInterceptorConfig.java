package com.config;
import com.interceptor.AuthorizationInterceptor;
import com.interceptor.CommonInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zengjie on 17/6/21.
 */
@Configuration
public class CommonInterceptorConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**").excludePathPatterns("/app");
        registry.addInterceptor(new AuthorizationInterceptor()).addPathPatterns("/app/**");
    }


}