package com.peanut.messagequeue.Config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQComponent {

    public final static String FIRST_QUEUE = "first_queue";
    public final static String SECOND_QUEUE = "second_queue";
    public final static String THIRD_QUEUE = "third_queue";
    public final static String FOUR_IOS_QUEUE = "four_ios_queue";
    public final static String FOUR_ANDROID_QUEUE = "four_android_queue";

    public final static String FIRST_DIRECT_EXCHANGE = "first_direct_exchange";
    public final static String SECOND_DIRECT_EXCHANGE = "second_direct_exchange";

    public final static String FANOUT_EXCHANGE = "fanout_exchange";
    public final static String TOPIC_EXCHANGE = "topic_exchange";

    public final static String FIRST_DIRECT_ROUTING_KEY = "first_direct_routing_key";
    public final static String SECOND_DIRECT_ROUTING_KEY = "second_direct_routing_key";
    public final static String FOUR_IOS_TOPIC_ROUTING_KEY = "four_ios_topic_routing_key";
    public final static String FOUR_ANDROID_TOPIC_ROUTING_KEY = "four_android_topic_routing_key";

    // 可以深入了解一些Queue、DirectExchange、FanoutExchange、TopicExchange和Binding这几个类的属性，针对性定制所需要的元素。

    /**
     * QUEUE
     */
    @Bean(name = FIRST_QUEUE)
    public Queue first_queue() {
        return new Queue(FIRST_QUEUE);
    }

    @Bean(name = SECOND_QUEUE)
    public Queue second_queue() {
        return new Queue(SECOND_QUEUE);
    }

    @Bean(name = THIRD_QUEUE)
    public Queue third_queue() {
        return new Queue(THIRD_QUEUE);
    }

    @Bean(name = FOUR_IOS_QUEUE)
    public Queue four_ios_queue() {
        return new Queue(FOUR_IOS_QUEUE);
    }

    @Bean(name = FOUR_ANDROID_QUEUE)
    public Queue four_android_queue() {
        return new Queue(FOUR_ANDROID_QUEUE);
    }

    /**
     * EXCHANGE
     */
    @Bean(name = FIRST_DIRECT_EXCHANGE)
    public DirectExchange first_direct_exchange()
    {
        return new DirectExchange(FIRST_DIRECT_EXCHANGE);
    }

    @Bean(name = SECOND_DIRECT_EXCHANGE)
    public DirectExchange second_direct_exchange()
    {
        return new DirectExchange(SECOND_DIRECT_EXCHANGE);
    }

    @Bean(name = FANOUT_EXCHANGE)
    public FanoutExchange fanout_exchange()
    {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    @Bean(name = TOPIC_EXCHANGE)
    public TopicExchange topic_exchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }


    @Bean
    public Binding first_direct_binding() {
        return BindingBuilder.bind(first_queue()).to(first_direct_exchange()).with(FIRST_DIRECT_ROUTING_KEY);
    }

    @Bean
    public Binding second_direct_binding() {
        return BindingBuilder.bind(second_queue()).to(second_direct_exchange()).with(SECOND_DIRECT_ROUTING_KEY);
    }

    @Bean
    public Binding first_fanout_binding() {
        return BindingBuilder.bind(first_queue()).to(fanout_exchange());
    }

    @Bean
    public Binding third_fanout_binding() {
        return BindingBuilder.bind(third_queue()).to(fanout_exchange());
    }

    @Bean
    public Binding bindingExchangeMessage() {
        return BindingBuilder.bind(four_ios_queue()).to(topic_exchange()).with(FOUR_IOS_TOPIC_ROUTING_KEY);
    }

    @Bean
    public Binding bindingExchangeMessages() {
        return BindingBuilder.bind(four_android_queue()).to(topic_exchange()).with(FOUR_ANDROID_TOPIC_ROUTING_KEY);
    }

}
