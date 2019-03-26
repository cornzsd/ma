package com.rabbitmq;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MQConfig {
    public static final String MIAOSHA_QUEUE = "miaosha.queue";
    public static final String QUEUE = "queue";
    public static final String TOP_QUEUE1 = "queue1";
    public static final String TOP_QUEUE2 = "queue2";
    public static final String TopicExchange="TopicExchange";
    public static final String FanoutExchange="FanoutExchange";
    public static final String HeadersExchange="HeadersExchange";
    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }
    @Bean
    public Queue queue1(){
        return new Queue(TOP_QUEUE1,true);
    }
    @Bean
    public Queue queue2(){
        return new Queue(TOP_QUEUE2,true);
    }
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange(TopicExchange);
    }
    @Bean
    public Binding topbinding1(){
        return BindingBuilder.bind(queue1()).to(topicExchange()).with("top.key1");
    }
    @Bean
    public Binding topbinding2(){
        return BindingBuilder.bind(queue2()).to(topicExchange()).with("top.#");
    }

    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FanoutExchange);
    }
    @Bean
    public  Binding fanoutbinding1(){
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }
    @Bean
    public  Binding fanoutbinding2(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }
    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HeadersExchange);
    }

    @Bean
    public  Binding headersbinding1(){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("key1","value1");
        map.put("key2","value2");
        return BindingBuilder.bind(queue1()).to(headersExchange()).whereAll(map).match();
    }

}


