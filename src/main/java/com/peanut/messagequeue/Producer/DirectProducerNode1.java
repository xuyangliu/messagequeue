package com.peanut.messagequeue.Producer;

import com.peanut.messagequeue.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class DirectProducerNode1 {

    private static final Logger LOGGER= LoggerFactory.getLogger(DirectProducerNode1.class);

    @Resource(name="node1RabbitTemplate")
    private RabbitTemplate node1RabbitTemplate;

    public void producer1() {

        User user = new User().setId(1).setName("Kenny").setPassword("123456789");
        LOGGER.info("Node1 Producer From first_direct_exchange use first_direct_routing_key : " + user);
        this.node1RabbitTemplate.convertAndSend(FIRST_DIRECT_EXCHANGE, FIRST_DIRECT_ROUTING_KEY, user);
    }

    public void producer2() {

        User user = new User().setId(2).setName("Northern").setPassword("987654321");
        LOGGER.info("Node1 Producer From second_direct_exchange use second_direct_routing_key : " + user);
        this.node1RabbitTemplate.convertAndSend(SECOND_DIRECT_EXCHANGE, SECOND_DIRECT_ROUTING_KEY, user);
    }
}
