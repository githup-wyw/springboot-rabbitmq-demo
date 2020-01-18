package com.wyw.rabbitmq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @description: 消息生产
 * @author: WYW
 * @date: 2020/1/18 001813:38
 */
@RestController
public class RabbitmqController {

    @Autowired
    Sender sender;

    /**
     * 生产消息--直连型
     */
    @GetMapping("/directSender")
    public String directSender(@RequestParam String msg){
        System.out.println("生产消息--直连型msg:"+msg);
        sender.sendDirect(msg);
        return "生产消息--直连型yes";
    }

    /**
     * 生产消息--主题型
     */
    @GetMapping("/topicSender")
    public String topicSender(@RequestParam String msg){
        System.out.println("生产消息--主题型msg:"+msg);
        sender.sendTopic(msg);
        return "生产消息--主题型yes";
    }

    /**
     * 生产消息--扇型
     */
    @GetMapping("/fanoutSender")
    public String fanoutSender(@RequestParam String msg){
        System.out.println("生产消息--扇型msg:"+msg);
        sender.sendFanout(msg);
        return "生产消息--主题型yes";
    }

}
