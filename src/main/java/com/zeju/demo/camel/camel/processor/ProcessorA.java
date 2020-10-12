package com.zeju.demo.camel.camel.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ProcessorA implements Processor{
    @Override
    public void process(Exchange exchange) throws Exception {
        String message = exchange.getIn().getBody().toString();
        System.out.println("Your mq message is: " + message);
    }
}
