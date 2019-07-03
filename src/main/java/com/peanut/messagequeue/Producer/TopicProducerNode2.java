package com.peanut.messagequeue.Producer;

import com.peanut.messagequeue.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class TopicProducerNode2 {

    private static final Logger LOGGER= LoggerFactory.getLogger(TopicProducerNode2.class);

    @Resource(name="node2RabbitTemplate")
    private RabbitTemplate node2RabbitTemplate;

    public void producer_ios() {

        User user = new User().setId(9).setName("ios").setPassword("333333333");
        LOGGER.info("Node2 Producer From topic_exchange to ios : " + user);
        this.node2RabbitTemplate.convertAndSend(TOPIC_EXCHANGE, FOUR_IOS_TOPIC_ROUTING_KEY, user);
    }

    public void producer_android() {

        User user = new User().setId(0).setName("Fanout").setPassword("000000000");
        LOGGER.info("Node2 Producer From topic_exchange to android : " + user);
        this.node2RabbitTemplate.convertAndSend(TOPIC_EXCHANGE, FOUR_ANDROID_TOPIC_ROUTING_KEY, user);
    }

}
