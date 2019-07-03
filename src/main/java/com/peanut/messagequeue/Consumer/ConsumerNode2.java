package com.peanut.messagequeue.Consumer;

import com.peanut.messagequeue.Entity.User;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class ConsumerNode2 {

    @RabbitListener(queues = {FIRST_QUEUE, SECOND_QUEUE, THIRD_QUEUE, FOUR_IOS_QUEUE, FOUR_ANDROID_QUEUE}, containerFactory="node2Factory")
    public void process(User message) {
        System.out.println("Node 2 Consumer : " + message);
    }
}