package com.wyw.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @description: 消息发送--生产消息
 * @author: WYW
 * @date: 2020/1/18 001813:15
 */
@Component
public class Sender {

    @Autowired
    RabbitTemplate rabbitTemplate;

    /**
     * 发送消息--直连型
     * @param msg 消息内容
     */
    public void sendDirect(String msg){
//        System.out.println("消息内容："+msg);
        rabbitTemplate.convertAndSend("direct-exchange","direct",msg);// 交换机名称、路由key、消息内容
    }

    /**
     * 发送消息--主题型
     * @param msg 消息内容
     */
    public void sendTopic(String msg){
//        System.out.println("消息内容："+msg);
        // 指定路由key（topic.man）发送
        rabbitTemplate.convertAndSend("topic-exchange","topic.man",msg);// 交换机名称、路由key、消息内容
        // 指定路由key（topic.开头的）发送
        rabbitTemplate.convertAndSend("topic-exchange","topic.#",msg);// 交换机名称、路由key、消息内容
    }

    /**
     * 发送消息--扇型
     * @param msg 消息内容
     */
    public void sendFanout(String msg){
//        System.out.println("消息内容："+msg);
        rabbitTemplate.convertAndSend("fanout-exchange",null,msg);// 交换机名称、路由key、消息内容
    }

    /**
     * 发送消息--延时队列TTL
     * @param msg 消息内容
     */
    public void sendDelayTTL(String msg){
//        System.out.println("消息内容："+msg);
        rabbitTemplate.convertAndSend("delay.exchange","delay.key",msg,message -> {
            // 设置该消息的到期时间
            message.getMessageProperties().setExpiration(String.valueOf(1000*30));
            return message;
        });// 交换机名称、路由key、消息内容、消息对象

//        rabbitTemplate.convertAndSend("delay.exchange","delay.key",msg);// 交换机名称、路由key、消息内容
    }

}
