package com.config;
import com.interceptor.AuthorizationInterceptor;
import com.interceptor.CommonInterceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by zengjie on 17/6/21.
 */
@Configuration
public class CommonInterceptorConfig extends WebMvcConfigurerAdapter {
	
	/**
	 * 用于拦截器中需要注入bean
	 * @return
	 */
	 @Bean
	AuthorizationInterceptor authorizationInterceptor() {
		return new AuthorizationInterceptor();
	}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CommonInterceptor()).addPathPatterns("/**").excludePathPatterns("/app");
        registry.addInterceptor(authorizationInterceptor()).addPathPatterns("/app/**");
    }


}