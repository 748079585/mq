package com.activemq.demo.config;

import javax.jms.Queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;

//@ComponentScan(basePackages = {"com.jim.framework.activemq"})
//@Configuration
public class ActivemqConfiguration {

    private static final String BROKER_URL="failover:(tcp://192.168.1.197:61616,tcp://192.168.1.161:61616)?randomize=true" ;

    @Bean
    public Queue productActiveMQQueue(){
        return new ActiveMQQueue("jim.queue.product");
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerQueue() {
        DefaultJmsListenerContainerFactory bean = new DefaultJmsListenerContainerFactory();
        bean.setConnectionFactory(new ActiveMQConnectionFactory(BROKER_URL));
        return bean;
    }

    @Bean
    public JmsMessagingTemplate jmsMessagingTemplate(){
        return new JmsMessagingTemplate(new ActiveMQConnectionFactory(BROKER_URL));
    }
}
