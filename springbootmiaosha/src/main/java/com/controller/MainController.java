package com.controller;


import com.model.Result;
import com.rabbitmq.MQSender;
import com.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String hello(Model model){
        model.addAttribute("user",userService.getUser(1));
        return  "hello";
    }
    @Autowired
    MQSender mqSender;

    @RequestMapping("/mq")
    @ResponseBody
    public Result mq(){
        String msg="Hello:mq";
        mqSender.send(msg);
        return  Result.success(msg);
    }
    @RequestMapping("/mq2")
    @ResponseBody
    public Result mq2(){
        String msg="Hello:mq";
        mqSender.send2(msg);
        return  Result.success(msg);
    }
    @RequestMapping("/mq3")
    @ResponseBody
    public Result mq3(){
        String msg="Hello:mq";
        mqSender.send3(msg);
        return  Result.success(msg);
    }
    @RequestMapping("/mq4")
    @ResponseBody
    public Result mq4(){
        String msg="Hello:mq";
        mqSender.send4(msg);
        return  Result.success(msg);
    }

}
