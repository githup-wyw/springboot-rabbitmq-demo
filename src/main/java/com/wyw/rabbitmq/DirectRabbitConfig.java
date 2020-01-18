package com.wyw.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 直连型交换机:直连接类型direct必须是生产者发布消息指定的routingKey和消费者在队列绑定时指定的routingKey完全相等时才能匹配到队列上
 * @author: WYW
 * @date: 2020/1/18 001813:04
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 创建队列
     */
    @Bean
    public Queue directQueue(){
        return new Queue("direct-queue",false);// 队列名称、是否持久化
    }

    /**
     * 创建直连型（direct）的交换机
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("direct-exchange",false,false);// 交换机名称、是否持久化、是否自动删除
    }

    /**
     * 队列和交换机进行绑定
     */
    @Bean
    public Binding directBinding(){
        return BindingBuilder.bind(directQueue()).to(directExchange()).with("direct");// 队列对象、交换机对象、路由key
    }


}
