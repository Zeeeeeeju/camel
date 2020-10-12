package com.zeju.demo.camel.camel.config;

import com.solacesystems.jms.SolConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class CamelConfig {

    @Bean(name = "solace")
    public JmsComponent solaceComponent(SolConnectionFactory connectionFactory) {
        JmsComponent jmsComponent = new JmsComponent();
        jmsComponent.setConnectionFactory(connectionFactory);
        return jmsComponent;
    }

    @Bean(name = "activemq")
    public JmsComponent activemqComponent() {
        ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory();
        cf.setBrokerURL("tcp://localhost:61616");
        cf.setUserName("admin");
        cf.setPassword("admin");

        JmsComponent jms = new JmsComponent();
        jms.setConnectionFactory(cf);
        return jms;
    }

}
