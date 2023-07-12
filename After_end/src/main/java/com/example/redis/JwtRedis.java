package com.example.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtRedis {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    public void save(String kay,Object value){
        redisTemplate.opsForValue().set(kay,value);
        // 设置有效期
        redisTemplate.expire(kay,24, TimeUnit.HOURS);
    }

    public Object get(String key){
        return redisTemplate.opsForValue().get(key);
    }
}
