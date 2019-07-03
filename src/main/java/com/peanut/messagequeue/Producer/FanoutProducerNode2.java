package com.peanut.messagequeue.Producer;

import com.peanut.messagequeue.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class FanoutProducerNode2 {

    private static final Logger LOGGER= LoggerFactory.getLogger(FanoutProducerNode2.class);

    @Resource(name="node2RabbitTemplate")
    private RabbitTemplate node2RabbitTemplate;

    public void producer() {

        User user = new User().setId(6).setName("Fanout").setPassword("444444444");
        LOGGER.info("Node2 Producer From fanout_exchange to first and third : " + user);
        this.node2RabbitTemplate.convertAndSend(FANOUT_EXCHANGE, "", user);
    }

}
