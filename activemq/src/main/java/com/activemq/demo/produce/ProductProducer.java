package com.activemq.demo.produce;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductProducer implements ProductSendMessage {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

//    @Autowired
//    private Queue productActiveMQQueue;
    
    @Value("${mast.jms.queue}")
    private String queue;


    @Override
    public void sendMessage(Object message) {

        this.jmsMessagingTemplate.convertAndSend(queue,message);
    }
}
