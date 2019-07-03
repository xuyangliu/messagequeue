package com.peanut.messagequeue.Producer;

import com.peanut.messagequeue.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class DirectProducerNode2 {

    private static final Logger LOGGER= LoggerFactory.getLogger(DirectProducerNode2.class);

    @Resource(name="node2RabbitTemplate")
    private RabbitTemplate node2RabbitTemplate;

    public void producer1(){

        User user = new User().setId(3).setName("Linda").setPassword("999999999");
        LOGGER.info("Node2 Producer From first_direct_exchange use first_direct_routing_key : " + user);
        this.node2RabbitTemplate.convertAndSend(FIRST_DIRECT_EXCHANGE, FIRST_DIRECT_ROUTING_KEY, user);
    }

    public void producer2(){

        User user = new User().setId(4).setName("Ping").setPassword("666666666");
        LOGGER.info("Node2 Producer From second_direct_exchange use second_direct_routing_key : " + user);
        this.node2RabbitTemplate.convertAndSend(SECOND_DIRECT_EXCHANGE, SECOND_DIRECT_ROUTING_KEY, user);
    }
}
