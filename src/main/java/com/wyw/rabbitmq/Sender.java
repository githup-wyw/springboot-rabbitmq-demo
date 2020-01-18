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
     * @param message 消息内容
     */
    public void sendDirect(String message){
//        System.out.println("消息内容："+message);
        rabbitTemplate.convertAndSend("direct-exchange","direct",message);// 交换机名称、路由key、消息内容
    }

    /**
     * 发送消息--主题型
     * @param message 消息内容
     */
    public void sendTopic(String message){
//        System.out.println("消息内容："+message);
        // 指定路由key（topic.man）发送
        rabbitTemplate.convertAndSend("topic-exchange","topic.man",message);// 交换机名称、路由key、消息内容
        // 指定路由key（topic.开头的）发送
        rabbitTemplate.convertAndSend("topic-exchange","topic.#",message);// 交换机名称、路由key、消息内容
    }

    /**
     * 发送消息--扇型
     * @param message 消息内容
     */
    public void sendFanout(String message){
//        System.out.println("消息内容："+message);
        rabbitTemplate.convertAndSend("fanout-exchange",null,message);// 交换机名称、路由key、消息内容
    }

}
