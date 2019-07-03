package com.peanut.messagequeue.Producer;

import com.peanut.messagequeue.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class FanoutProducerNode1 {

    private static final Logger LOGGER= LoggerFactory.getLogger(FanoutProducerNode1.class);

    @Resource(name="node1RabbitTemplate")
    private RabbitTemplate node1RabbitTemplate;

    public void producer() {

        User user = new User().setId(5).setName("Fanout").setPassword("777777777");
        LOGGER.info("Node1 Producer From fanout_exchange to first and third : " + user);
        this.node1RabbitTemplate.convertAndSend(FANOUT_EXCHANGE, "", user);
    }

}
