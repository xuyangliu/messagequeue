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

/**
 * RabbitMQConfig - RabbitMQ相关配置
 */
@Configuration
public class RabbitMQConfigNode2 {

    @Autowired
    private Jackson2JsonMessageConverter jackson2JsonMessageConverter;

    /**
     * application.yaml获取对应RabbitMQ节点的属性信息，通过获取到的属性信息创建ConnectionFactory连接工厂
     *
     * @return ConnectionFactory
     */
    @Bean(name = "node2ConnectionFactory")
    @ConfigurationProperties(prefix = "rabbitmq.node2")
    public ConnectionFactory node2ConnectionFactory() {
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
    @Bean(name = "node2RabbitTemplate")
    public RabbitTemplate node2RabbitTemplate(@Qualifier("node2ConnectionFactory") ConnectionFactory connectionFactory) {
        RabbitTemplate node2RabbitTemplate = new RabbitTemplate(connectionFactory);
        node2RabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return node2RabbitTemplate;
    }

    /**
     * 消息消费端Consumer需要去配置，采用工厂模式生产DirectMessageListenerContainer或者SimpleMessageListenerContainer
     * RabbitListener注解的containerFactory属性可以指定一个RabbitListenerContainerFactory的bean。
     * @param configurer
     * @param connectionFactory
     * @return
     */
    @Bean(name = "node2Factory")
    public RabbitListenerContainerFactory node2Factory(SimpleRabbitListenerContainerFactoryConfigurer configurer,
                                                       @Qualifier("node2ConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(jackson2JsonMessageConverter);
//        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);// 开启ACK，自动、手动
//        factory.setTaskExecutor(Executors.newFixedThreadPool(600));// 设置线程池
//        factory.setPrefetchCount(50);// 每个消费者获取最大投递数量 默认50
//        factory.setConcurrentConsumers(10);// 消费者数量，默认10
        configurer.configure(factory, connectionFactory);
        return factory;
    }
}
