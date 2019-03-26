package com.service;


import com.model.KeyPrefix;

public interface RedisService {

    public  <T>T get(KeyPrefix prefix,String key, Class<?> clazz);
    public  <T>boolean set(KeyPrefix prefix, String key, T value);
}
