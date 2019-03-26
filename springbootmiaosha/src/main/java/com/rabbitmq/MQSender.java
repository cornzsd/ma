package com.rabbitmq;

import com.model.MiaoshaMessage;
import com.service.impl.RedisServiceImpl;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSender {

    @Autowired
    AmqpTemplate amqpTemplate;

    public void miaoshasend(MiaoshaMessage message){
        String msg = RedisServiceImpl.beanToString(message);
        System.out.println("send:"+message);
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE,msg);
    }
    public void send(Object message){
        String msg = RedisServiceImpl.beanToString(message);
        System.out.println("send:"+message);
        amqpTemplate.convertAndSend(MQConfig.QUEUE,message);
    }

    public void send2(Object message){
        String msg = RedisServiceImpl.beanToString(message);
        System.out.println("send1:"+message);
        amqpTemplate.convertAndSend(MQConfig.TopicExchange,"top.key1",message+"==top1");
        amqpTemplate.convertAndSend(MQConfig.TopicExchange,"top.key2",message+"==top2");
    }
    public void send3(Object message){
        String msg = RedisServiceImpl.beanToString(message);
        System.out.println("send1:"+message);
        amqpTemplate.convertAndSend(MQConfig.FanoutExchange,"",message+"==top1");
      //  amqpTemplate.convertAndSend(MQConfig.TopicExchange,"top.key2",message+"==top2");
    }
    public void send4(Object message){
        String msg = RedisServiceImpl.beanToString(message);
        System.out.println("send1:"+message);
        MessageProperties properties=new MessageProperties();
        properties.setHeader("key1","value1");
        properties.setHeader("key2","value3");
        Message message1=new Message(msg.getBytes(),properties);
        amqpTemplate.convertAndSend(MQConfig.HeadersExchange,"",message1);
        //  amqpTemplate.convertAndSend(MQConfig.TopicExchange,"top.key2",message+"==top2");
    }
}
