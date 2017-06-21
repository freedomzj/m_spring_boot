package com.controller;

import com.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zengjie on 17/6/20.
 */
@Controller
public class MainController {

    @RequestMapping(value={"/index","/"}, method= RequestMethod.GET)
    public String main(Model model, HttpServletRequest request){
        User u=new User(1,"zengjie","123456");
        List<User> users=new ArrayList<User>();
        users.add(u);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("password","123456");
        map.put("username","曾杰");
        model.addAttribute("dataMap",map);
        model.addAttribute("users",users);
        model.addAttribute("username","zengjie");
        model.addAttribute("dec","这是一个逗逼的世界");
        return "main";
    }
}
