package com.peanut.messagequeue.Consumer;

import com.peanut.messagequeue.Entity.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.peanut.messagequeue.Config.RabbitMQComponent.*;

@Component
public class ConsumerNode1 {

    @RabbitListener(queues = {FIRST_QUEUE, SECOND_QUEUE, THIRD_QUEUE, FOUR_IOS_QUEUE, FOUR_ANDROID_QUEUE}, containerFactory="node1Factory")
    public void process(@Payload User message, @Headers Map<String, Object> properties, Channel channel) {
        System.out.println("Node 1 Consumer : " + message);

//        Long deliveryTag = (Long) properties.get(AmqpHeaders.DELIVERY_TAG);
//        System.out.println("deliveryTag:" + deliveryTag);
//        // 限流处理：0表示对消息体的大小无限制，1表示每次只允许消费一条，false表示该限制不作用于channel，只作用于该Consumer层。
//        channel.basicQos(0, 1, false);
//        // 配置了manual，进行手工ACK,不批量ack，multiple:当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
//        channel.basicAck(deliveryTag, false);
    }
}
