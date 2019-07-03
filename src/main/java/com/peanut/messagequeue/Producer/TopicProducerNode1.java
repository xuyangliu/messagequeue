package com.peanut.messagequeue.Producer;

import com.peanut.messagequeue.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class TopicProducerNode1 {

    private static final Logger LOGGER= LoggerFactory.getLogger(TopicProducerNode1.class);

    @Resource(name="node1RabbitTemplate")
    private RabbitTemplate node1RabbitTemplate;

    public void producer_ios() {

        User user = new User().setId(7).setName("ios").setPassword("111111111");
        LOGGER.info("Node1 Producer From topic_exchange to ios : " + user);
        this.node1RabbitTemplate.convertAndSend(TOPIC_EXCHANGE, FOUR_IOS_TOPIC_ROUTING_KEY, user);
    }

    public void producer_android() {

        User user = new User().setId(8).setName("Fanout").setPassword("222222222");
        LOGGER.info("Node1 Producer From topic_exchange to android : " + user);
        this.node1RabbitTemplate.convertAndSend(TOPIC_EXCHANGE, FOUR_ANDROID_TOPIC_ROUTING_KEY, user);
    }

}
