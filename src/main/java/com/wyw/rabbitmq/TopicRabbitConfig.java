package com.wyw.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 主题交换机配置:topic可以进行模糊匹配,
 * 可以使用星号*和井号#这两个通配符来进行模糊匹配，
 * 其中*星号可以代替一个单词；#井号可以替代零个或更多的单词
 * 主题类型的转发器的消息不能随意的设置选择键（routing_key），必须是由点隔开的一系列的标识符组成
 * @author: WYW
 * @date: 2020/1/18 001815:34
 */
@Configuration
public class TopicRabbitConfig {

    //路由key
    public final static String man = "topic.man";
    public final static String woman = "topic.woman";

    /**
     * 创建队列1
     */
    @Bean
    public Queue firstQueue() {
        return new Queue("topic-queue-man");// 队列名
    }

    /**
     * 创建队列2
     */
    @Bean
    public Queue secondQueue() {
        return new Queue("topic-queue-woman");// 队列名
    }

    /**
     * 创建主体类型的交换机
     */
    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }

    /**
     * 将firstQueue和topic-exchange绑定,而且绑定的键值为topic.man
     * 这样只要是消息携带的路由键是topic.man,才会分发到该队列
     */
    @Bean
    Binding bindingExchangeMessage1() {
        return BindingBuilder.bind(firstQueue()).to(topicExchange()).with(man);
    }

    /**
     * 将secondQueue和topic-exchange绑定,而且绑定的键值为用上通配路由键规则topic.#
     * 这样只要是消息携带的路由键是以topic.开头,都会分发到该队列
     */
    @Bean
    Binding bindingExchangeMessage2() {
        return BindingBuilder.bind(secondQueue()).to(topicExchange()).with("topic.#");
    }


}
