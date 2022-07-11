package com.liu.project01;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class Project01ApplicationTests {


    @Autowired
    RedisTemplate redisTemplate;

    @Test
    void Testlock01() {
        ValueOperations ops = redisTemplate.opsForValue();
        //Boolean isLocked = ops.setIfAbsent("k1", "v1");//只有当设置的Key不存在的时候才能设置成功
        //加上超时时间
        Boolean isLocked = ops.setIfAbsent("k1", "v1", 5, TimeUnit.SECONDS);

        if (isLocked) {    //如果设置成功,就相当于获得锁
            ops.set("name", "lzw");
            String name = (String) ops.get("name");
            System.out.println("name:" + name);
            Integer.valueOf("SSADASD");  //丢出一个异常,此时后面的释放锁操作不在执行  可以通过ttl自动释放锁
            //操作结束释放锁
            redisTemplate.delete("k1");
        } else {
            System.out.println("该锁被占用中");
        }


    }

    @Autowired
    RedisScript redisScript;

    @Test
    public void testLock02() {
        ValueOperations vos = redisTemplate.opsForValue();
        String value = UUID.randomUUID().toString();
        Boolean isLocked = vos.setIfAbsent("k1", value, 120, TimeUnit.SECONDS);

        if (isLocked) {
            vos.set("name", "liuzhengwei");
            String name = (String) vos.get("name");
            System.out.println("name:" + name);
            System.out.println((vos.get("k1")));
            Boolean result = (Boolean) redisTemplate.execute(redisScript, Collections.singletonList("k1"), value);
            System.out.println(result);
        } else {
            System.out.println("锁被占用中,请稍后重试");
        }
    }

}
