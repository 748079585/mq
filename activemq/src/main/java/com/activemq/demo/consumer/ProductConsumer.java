package com.activemq.demo.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ProductConsumer {

//    @JmsListener(destination = "jim.queue.product",containerFactory = "jmsListenerContainerQueue")
	@JmsListener(destination = "${mast.jms.queue}")
    public void receiveQueue(String text) {
        System.out.println("Consumer,productId:"+text);
        log.info(text);
    }

}
