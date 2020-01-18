package com.wyw.rabbitmq;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @description: 消息接受--消息消费
 * @author: WYW
 * @date: 2020/1/18 001813:20
 */
@Component
public class Receiver {

    /**
     * 监听队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "direct-queue")
    public void direct(String msg, Message message, Channel channel){
        System.out.println("direct-queue接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 监听队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "topic-queue-man")
    public void topicMan(String msg, Message message, Channel channel){
        System.out.println("topic-queue-man接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 监听队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "topic-queue-woman")
    public void topicWoman(String msg, Message message, Channel channel){
        System.out.println("topic-queue-woman接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 监听队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "fanout.A")
    public void fanoutA(String msg, Message message, Channel channel){
        System.out.println("fanout.A接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 监听队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "fanout.B")
    public void fanoutB(String msg, Message message, Channel channel){
        System.out.println("fanout.B接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 监听队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "fanout.C")
    public void fanoutC(String msg, Message message, Channel channel){
        System.out.println("fanout.C接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 监听延时队列消息
     * @param msg 消息内容
     * @param message 消息对象
     * @param channel 通道
     */
    @RabbitListener(queues = "delay.queue.dxl")
    public void delayQueueTTL(String msg, Message message, Channel channel){
        System.out.println("delay.queue.dxl接受消息:"+msg);
//        System.out.println("消息对象："+message);
//        System.out.println("消息通道："+channel);
        // 该消息的index
        final long deliveryTag = message.getMessageProperties().getDeliveryTag();

        // 消费消息
        Boolean consume_result = consume(msg);

        // 消息手动应答
        msgAnswer(channel,deliveryTag,consume_result,msg);
    }

    /**
     * 消息应答
     * @param channel 通道
     * @param deliveryTag 该消息索引
     * @param rel 结果
     * @param msg 消息内容
     */
    public void msgAnswer(Channel channel,long deliveryTag,Boolean rel,String msg){
        // 消息手动应答
        if (rel) {
            System.out.println("消费成功msg："+msg);
            //ACK
            try {
                channel.basicAck(deliveryTag, false);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("删除该消息--异常：e" + e);
            }
        } else {

            System.out.println("消费失败msg" + msg);
            // nack 重新入队
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("重新入队--异常：e" + e);
            }
        }
    }

    /**
     * 消费消息
     * @param msg 消息内容
     * @return 成功或失败 true/false
     */
    public Boolean consume(String msg){
        if (msg.equals("no"))
            return false;
        return true;
    }


}
