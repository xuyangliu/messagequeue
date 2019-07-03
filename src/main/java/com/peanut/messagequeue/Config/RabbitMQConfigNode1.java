package com.peanut.messagequeue.Config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * RabbitMQConfig - RabbitMQ相关配置
 */
@Configuration
public class RabbitMQConfigNode1 {

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    /**
     * application.yaml获取对应RabbitMQ节点的属性信息，通过获取到的属性信息创建ConnectionFactory连接工厂
     *
     * @return ConnectionFactory
     */
    @Primary
    @Bean(name = "node1ConnectionFactory")
    @ConfigurationProperties(prefix = "rabbitmq.node1")
    public ConnectionFactory node1ConnectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
//        connectionFactory.setPublisherConfirms(true); // 开启发送确认，这些参数可以放在application.yaml中进行配置。
//        connectionFactory.setPublisherReturns(true); // 开启发送失败退回
        return connectionFactory;
    }

    /**
     * 消息生产端Producer需要去配置
     * 根据指定名称的ConnectionFactory创建RabbitTemplate消息模板，提供了丰富的发送消息方法。
     * @param connectionFactory
     * @return RabbitTemplate
     */
    @Bean(name = "node1RabbitTemplate")
    public RabbitTemplate node1RabbitTemplate(@Qualifier("node1ConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate node1RabbitTemplate = new RabbitTemplate(connectionFactory);
        node1RabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
//        node1RabbitTemplate.setConfirmCallback(confirmCallback);
////        node1RabbitTemplate.setReturnCallback(returnCallback);
        return node1RabbitTemplate;
    }

    /**
     * 消息消费端Consumer需要去配置，采用工厂模式生产DirectMessageListenerContainer或者SimpleMessageListenerContainer
     * RabbitListener注解的containerFactory属性可以指定一个RabbitListenerContainerFactory的bean。
     * @param configurer
     * @param connectionFactory
     * @return
     */
    @Bean(name = "node1Factory")
    public RabbitListenerContainerFactory node1Factory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                       @Qualifier("node1ConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(jackson2JsonMessageConverter);
//        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);// 开启ACK，自动、手动
//        factory.setTaskExecutor(Executors.newFixedThreadPool(600));// 设置线程池
//        factory.setPrefetchCount(50);// 每个消费者获取最大投递数量 默认50
//        factory.setConcurrentConsumers(10);// 消费者数量，默认10
        configurer.configure(factory, connectionFactory);
        return factory;
    }

    /**
     * 用于监听Server端给我们返回的确认请求,消息到了exchange，ack 就返回true
     */
    private final RabbitTemplate.ConfirmCallback  confirmCallback = (correlationData, ack, cause) -> {
        System.out.println("correlationData:" + correlationData);
        System.out.println("ack:" + ack);
        if (ack){
            System.out.println("将msg-db数据更新为处理成功");
        } else {
            System.out.println("记录异常日志...，后续会有补偿机制(定时器)");
        }
    };

    /**
     * 监听对不可达的消息进行后续处理;
     * 不可达消息：指定的路由key路由不到。
     */
    private final RabbitTemplate.ReturnCallback returnCallback = (message, replyCode, replyText,
                                                                  exchange, routingKey) -> System.out.println("return exchange:" + exchange + ", routingKey:" + routingKey +
            ", replyText:" + replyText);

}
