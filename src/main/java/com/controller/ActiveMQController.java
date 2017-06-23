package com.controller;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.Queue;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Map;
import java.util.HashMap;

/**
 * Created by zengjie on 17/6/23.
 */
@Controller
@RequestMapping("active")
public class ActiveMQController {

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration freemarkerConfig;

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


    @RequestMapping("sendEmail")
    @ResponseBody
    String home() {
        try {
            sendEmail();
            return "Email Sent!";
        } catch (Exception ex) {
            ex.printStackTrace();
            return "Error in sending email: " + ex;
        }
    }

    private void sendEmail() throws Exception {
        MimeMessage message = sender.createMimeMessage();

       // MimeMessage helper = new MimeMessage(message);

        Map<String, Object> model = new HashMap();

        model.put("user", "qpt");

        // set loading location to src/main/resources
        // You may want to use a subfolder such as /templates here
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");
        Template t = freemarkerConfig.getTemplate("email.ftl");
        String text = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("1063304800@qq.com"));// 写上收件人
        message.setFrom(new InternetAddress("354253175@qq.com"));// 写上发送人，即发送方QQ邮箱
        message.setText(text); // set to html
        message.setSubject("Hi");
        message.setContent(text, "text/html;charset=UTF-8");// 内容
        sender.send(message);
    }
}
