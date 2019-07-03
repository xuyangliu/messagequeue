package com.peanut.messagequeue;
import com.peanut.messagequeue.Producer.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RabbitMQTest {
    @Autowired
    private DirectProducerNode1 directProducerNode1;

    @Autowired
    private DirectProducerNode2 directProducerNode2;

    @Autowired
    private FanoutProducerNode1 fanoutProducerNode1;

    @Autowired
    private FanoutProducerNode2 fanoutProducerNode2;

    @Autowired
    private TopicProducerNode1 topicProducerNode1;

    @Autowired
    private TopicProducerNode2 topicProducerNode2;

    @Test
    public void producerNode1_hello() throws Exception {
        directProducerNode1.producer1();
        directProducerNode1.producer2();
        fanoutProducerNode1.producer();
        topicProducerNode1.producer_ios();
        topicProducerNode1.producer_android();

    }




}
