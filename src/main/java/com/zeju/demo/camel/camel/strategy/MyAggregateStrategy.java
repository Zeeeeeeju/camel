package com.zeju.demo.camel.camel.strategy;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class MyAggregateStrategy implements AggregationStrategy {
    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        System.out.println(oldExchange);
        System.out.println(newExchange);
        return newExchange;
    }
}
