package com.ssm.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Repository;

/**
 * Project Name: Web_App
 * Des:
 * Created by Even on 2018/10/19
 */
@Repository
public class DirectMessageListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        System.out.println("consumer receive message--------------->:" + message);
        /*第一个参数deliveryTag唯一标识了channel的一个传递，第二个参数multiple，是否批量回复小于deliveryTag的消息*/
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
    }
}
