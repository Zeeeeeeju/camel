package com.zeju.demo.camel.camel.bean;

import java.util.Arrays;
import java.util.List;

public class SplitBean {

    public static List<String> splitMessage(String body){
        return Arrays.asList(body.split("/"));
    }

}
