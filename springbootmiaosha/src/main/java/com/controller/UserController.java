package com.controller;

import com.model.MiaoshaUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
public class UserController {

    @RequestMapping("getUser")
    @ResponseBody
    public  String login(MiaoshaUser miaoshaUser){
        return "success";
    }
}
