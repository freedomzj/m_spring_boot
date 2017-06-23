package com;

import javax.jms.JMSException;

import com.message.Producer;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
/**
 * Created by zengjie on 17/6/23.
 */
/**
 * Integration tests for demo application.
 *
 * @author Eddú Meléndez
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleActiveMqTests {

    @Rule
    public OutputCapture outputCapture = new OutputCapture();

    @Autowired
    private Producer producer;

    @Test
    public void sendSimpleMessage() throws InterruptedException, JMSException {
        this.producer.send("Test message");
        Thread.sleep(1000L);
        assertThat(this.outputCapture.toString().contains("Test message")).isTrue();
    }

}
