package com.common;

import org.springframework.boot.autoconfigure.web.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zengjie on 17/6/20.
 */
public class MyErrorViewResolver  implements ErrorViewResolver {

    @Override
    public ModelAndView resolveErrorView(HttpServletRequest request,
                                         HttpStatus status, Map<String, Object> model) {
        ModelAndView mv=new ModelAndView("/error/404");
        // Use the request or status to optionally return a ModelAndView
        return mv;
    }
}