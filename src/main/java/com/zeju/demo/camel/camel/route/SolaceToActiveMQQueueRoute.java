package com.zeju.demo.camel.camel.route;

import com.zeju.demo.camel.camel.processor.ProcessorA;
import com.zeju.demo.camel.camel.processor.ProcessorB;
import com.zeju.demo.camel.camel.processor.ProcessorC;
import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.MulticastDefinition;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class SolaceToActiveMQQueueRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(Throwable.class)
                .log(LoggingLevel.ERROR, "Error occurs when deal with message: with body: ${body}");

        from("solace:test1")
            .to("activemq:queue1")
                .to("direct:directRouteA")
                .to("direct:directRouteB")
                .to("direct:directRouteC")
                .to("direct:directRouteD")
                .to("direct:dynamicRoute")
                .to("direct:beanService");

        from("direct:directRouteA")
                .to("activemq:queue2");

        from("direct:directRouteB")
                .process(ProcessorA::new);

        from("direct:directRouteC")
                .choice()
                .when(body().isEqualTo("asd"))
                .process(ProcessorB::new)
                .otherwise()
                .process(ProcessorC::new)
                .endChoice();

        //复制exchange, 多份发送 Muticast可以拆分后聚合
        //并发
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        MulticastDefinition multicastDefinition = from("direct:directRouteD").multicast();
        multicastDefinition.setParallelProcessing(true);
        multicastDefinition.setExecutorService(executorService);
        multicastDefinition.to(
                "activemq:queue2","activemq:queue3"
        ).end();

        //单向叉子, 也是复制exchange, 多份发送
//        from("direct:directRouteD")
//                .wireTap("activemq:queue2")
//                .wireTap("activemq:queue3");

        //动态路由, 多份发送
        from("direct:dynamicRoute").choice().when(body().contains(",")).setExchangePattern(ExchangePattern.InOnly)
                .recipientList().body().delimiter(",")
                .end();

        //调用方法
        from("direct:beanService").bean("camelService","test");

    }
}
