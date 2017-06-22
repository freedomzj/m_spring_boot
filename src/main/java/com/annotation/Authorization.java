package com.annotation;

/**
 * Created by zengjie on 17/6/22.
 */
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在Controller的方法中使用此注解,该方法在调用时会进行token验证
 * @author zengjie
 * @see com.interceptor.AuthorizationInterceptor
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Authorization {
}