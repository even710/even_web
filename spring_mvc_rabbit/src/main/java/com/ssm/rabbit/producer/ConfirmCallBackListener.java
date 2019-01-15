package com.ssm.rabbit.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/11/13
 */
@Service("confirmCallBackListener")
public class ConfirmCallBackListener implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean ack) {
        System.out.println("correlationData of confirm:" + correlationData + ",ack=" + ack);
        if (ack)
            System.out.println("发送成功");
        else
            System.out.println("发送失败，重新发送/不发送");

    }
}
