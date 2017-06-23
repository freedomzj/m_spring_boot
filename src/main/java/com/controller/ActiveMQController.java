package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Queue;

/**
 * Created by zengjie on 17/6/23.
 */
@Controller
@RequestMapping("active")
public class ActiveMQController {

    @RequestMapping("index")
    public String index() {
        return "activeMQAjax";
    }

    @RequestMapping("amq")
    public String amq() {
        return "a";
    }

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    @RequestMapping(value = "send/{msg}",method = RequestMethod.GET)
    @ResponseBody
    public String sendMessage(@PathVariable String msg){
       jmsMessagingTemplate.convertAndSend(queue, msg);
        return "成功发送"+msg;
    }

}
