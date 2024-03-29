package com.tensquare.rabbit.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "itcast")
public class Consumer1 {
    @RabbitHandler
    public void getMsg(String msg) {
        System.out.println("itcast：" + msg);
    }
}
