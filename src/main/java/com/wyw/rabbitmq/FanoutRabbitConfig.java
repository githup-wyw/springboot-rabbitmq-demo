package com.wyw.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 扇型交换机:将消息发送给所有绑定它的队列，路由键无需配置,配置也不起作用
 * @author: WYW
 * @date: 2020/1/18 001816:57
 */
@Configuration
public class FanoutRabbitConfig {

    /**
     * 创建队列a
     */
    @Bean
    public Queue queueA() {
        return new Queue("fanout.A");
    }

    /**
     * 创建队列b
     */
    @Bean
    public Queue queueB() {
        return new Queue("fanout.B");
    }

    /**
     * 创建队列c
     */
    @Bean
    public Queue queueC() {
        return new Queue("fanout.C");
    }

    /**
     * 创建扇型交换机
     */
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }

    /**
     * 队列a绑定扇型交换机
     */
    @Bean
    Binding bindingExchangeA() {
        return BindingBuilder.bind(queueA()).to(fanoutExchange());
    }

    /**
     * 队列b绑定扇型交换机
     */
    @Bean
    Binding bindingExchangeB() {
        return BindingBuilder.bind(queueB()).to(fanoutExchange());
    }

    /**
     * 队列c绑定扇型交换机
     */
    @Bean
    Binding bindingExchangeC() {
        return BindingBuilder.bind(queueC()).to(fanoutExchange());
    }


}
