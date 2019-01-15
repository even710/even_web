package com.ssm.rabbit.producer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/10/19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-context.xml"})
public class MessageProducerTest {
    @Resource
    private MessageProducer messageProducer;

    @Test
    public void sendMessageTest() {
        int a = 1000;
        while (a > 0) {
            messageProducer.sendMessage("Hello, this is rabbitMQ test" + a);
            a--;
        }
    }
}

