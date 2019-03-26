package com.controller;

import com.model.Result;
import com.model.User;
import com.model.UserKey;
import com.service.RedisService;
import com.service.impl.RedisServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisController {
    /**
     * StringRedisTemplate继承了RedisTemplate。继承RedisTempalte，
     * 与RedisTemplate不同的是设置了序列化策略，使用StringRedisSerializer类来
     * 序列化key-value，以及List、Hash、Set。在这里，我们直接用就行了。
     */
/*
    @Autowired
    private StringRedisTemplate redisClient;*/
  /*  @Autowired
    private RedisTemplate redisTemplate;*/

   /* @RequestMapping("test")
    @ResponseBody
    public String test(String para) throws Exception{
        redisClient.opsForValue().set("testenv1", "aaaaaa");
        String str = redisClient.opsForValue().get("testenv1");
        return str;
    }
*/
   @Autowired
    private   RedisServiceImpl redisService;
    @RequestMapping("/test2")
    @ResponseBody
    public Result test(String para) throws Exception{
        User user=new  User();
        user.setId(1);
        user.setName("aaa");
        redisService.set(UserKey.getById,"1",user);
        User str=redisService.get(UserKey.getById,"1",User.class);
        return Result.success(str);
    }
}
