package com.liu.project01.config;//@date :2022/4/26 16:38


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //Key序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //Value序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //Hash Key
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //Hash Value
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        //注入连接工厂
        redisTemplate.setConnectionFactory(factory);

        return redisTemplate;
    }


    @Bean
    public DefaultRedisScript<Long> defaultRedisScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        //lua脚本位置和application.yml同级目录
        redisScript.setLocation(new ClassPathResource("stock.lua"));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
