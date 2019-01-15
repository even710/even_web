package com.ssm.rabbit.producer;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.stereotype.Service;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/14
 */
@Service("returnCallBackListener")
public class ReturnCallBackListener implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        String msId = message.getMessageProperties().getMessageId();
        System.out.println("return--message: msId:" + msId + ",msgBody:" + new String(message
                .getBody())
                + ",replyCode:" + replyCode + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:"
                + routingKey);

    }
}
