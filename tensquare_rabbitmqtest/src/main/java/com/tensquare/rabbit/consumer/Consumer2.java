package com.tensquare.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itheima")
public class Consumer2 {
    @RabbitHandler
    public void getMsg(String msg) {
        System.out.println("itheima：" + msg);
    }
}
