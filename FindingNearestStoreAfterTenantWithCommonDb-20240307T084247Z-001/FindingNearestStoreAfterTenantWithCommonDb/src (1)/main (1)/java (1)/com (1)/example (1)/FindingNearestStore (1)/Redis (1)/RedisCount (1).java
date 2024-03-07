package com.example.FindingNearestStore.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisCount {

    @Autowired
    private RedisTemplate<String,String> redisTemplate;
     public Long incrementRedisKey(String redisKey){
     Long count= redisTemplate.opsForValue().increment(redisKey,1);
     return count;
     }

     public Boolean hasKey(String key){
         return redisTemplate.hasKey(key);
     }
     public void setKey(String key,long value){

         redisTemplate.opsForValue().set(key, String.valueOf(value));
     }

}
