package com.zeju.demo.camel.camel.service;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class CamelService {

    public static void test(Exchange exchange){
        System.out.println("[CamelService]"+exchange.getIn().getBody().toString());
    }

}
