package com.ssm.rabbit.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/10/19
 */
@Service
public class MessageProducer {

    @Resource
    private RabbitTemplate amqpTemplate;

    public void sendMessage(Object message) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setMessageId("1");
        Message message1 = new SimpleMessageConverter().toMessage(message, messageProperties);
        amqpTemplate.convertAndSend("queueTestKey", message1, new CorrelationData("1"));
    }
}
