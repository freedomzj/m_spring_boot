package com.message;

/**
 * Created by zengjie on 17/6/23.
 */
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    @JmsListener(destination = "sample.queue")
    public void receiveQueue(String text) {
        System.out.println(text);
    }

    @JmsListener(destination = "zengjie发布任务队列")
    public void receiveZQueue(String text) {
        System.out.println(text);
    }

}
