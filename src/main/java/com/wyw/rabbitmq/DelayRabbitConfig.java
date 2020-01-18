package com.wyw.rabbitmq;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 延时队列配置
 * @author: WYW
 * @date: 2020/1/18 001817:59
 */
@Configuration
public class DelayRabbitConfig {

    /**
     * AMQP协议和RabbitMQ队列本身没有直接支持延迟队列功能
     * 但是我们可以通过RabbitMQ的两个特性来曲线实现延迟队列：
     * Time To Live(TTL) 消息的到期时间
     * Dead Letter Exchanges（DLX）死信交换机
     */

    /**
     * 准备工作：
     * 1.生产延时消息的队列配置：队列(TTL)、交换机、路由key
     * 2.接受延时消息的队列配置：队列、交换机（DXL）、路由key
     *
     * 实现过程：消息——>生产延时消息的队列（TTL）——>交换机（DXL）——>监听接受延时消息的队列进行消费
     * 说明：消息在生产延时消息的队列中过期后通过DXL交换机转发到接受延时消费的队列
     */

    /**1、第一种：直接设置 Queue 延迟时间
     * params.put("x-message-ttl", 5 * 1000);
     * 2、第二种：每次发送消息动态设置延迟时间,发送消息时配置
     * rabbitTemplate.convertAndSend(book, message -> {
     * message.getMessageProperties().setExpiration(2 * 1000 + "");
     * return message;
     * });
     * 3.当然二者是兼容的,默认是时间小的优
     */


    /**
     * 创建延时消息生产队列
     */
    @Bean
    public Queue delayQueue(){
        Map<String,Object> params = new HashMap<>();
        // x-dead-letter-exchange 声明了队列里的死信转发到的DLX名称，
        params.put("x-dead-letter-exchange", "delay.exchange.dxl");
        // x-dead-letter-routing-key 声明了这些死信在转发时携带的 routing-key 名称。
        params.put("x-dead-letter-routing-key", "delay.key.dxl");
        // x-message-ttl 声明队列的消息统一的到期时间；如果有特定的消息指定了到期时间，默认使用时间小的
        params.put("x-message-ttl",60*1000);
        return new Queue("delay.queue",true,false,false,params);// 延时队列名、是否持久、是否独有、是否自动删除、其他设置map对象
    }

    /**
     * 创建交换机（生产延时消息的队列交换机）
     * @return
     */
    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange("delay.exchange");
    }

    /**
     * 绑定交换机和延时消息生产队列，并指定路由key
     */
    @Bean
    Binding delayBinging(){
        return BindingBuilder.bind(delayQueue()).to(delayExchange()).with("delay.key");
    }

    //-----上面为生产延时消息配置-------下面为接受延时消息队列配置-------------------------------------------------------

    /**
     * 创建延时消息接收的队列
     */
    @Bean
    public Queue delayQueueDXL(){
        return new Queue("delay.queue.dxl");
    }

    /**
     * 创建交换机(接收延时消息队列交换机)
     */
    @Bean
    public TopicExchange exchangeDXL(){
        return new TopicExchange("delay.exchange.dxl");
    }

    /**
     * 延时消息接收的队列与接收交换机进行绑定，并指定路由key
     */
    @Bean
    Binding delayBingingDXL(){
        return BindingBuilder.bind(delayQueueDXL()).to(exchangeDXL()).with("delay.key.dxl");
    }


}
