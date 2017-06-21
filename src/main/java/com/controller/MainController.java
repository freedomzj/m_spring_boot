package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zengjie on 17/6/20.
 */
@Controller
public class MainController {

    @RequestMapping(value={"/index","/"}, method= RequestMethod.GET)
    public String main(Model model, HttpServletRequest request){
        request.setAttribute("basePath",request.getContextPath());
        model.addAttribute("username","zengjie");
        model.addAttribute("dec","这是一个逗逼的世界");
        return "main";
    }
}
