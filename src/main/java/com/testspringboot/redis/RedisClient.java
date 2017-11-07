package com.testspringboot.redis;

import org.redisson.Redisson;
import org.redisson.api.RSetCache;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class RedisClient {

    private RedissonClient redisson;

    @PostConstruct
    private void init(){
        Config config = new Config();
    /*    config.useSentinelServers()
                .setRetryAttempts(3)
                .setMasterName("0")
                .setPassword("redis")
                .addSentinelAddress("redis://127.0.0.1:6379");*/

        config.useSingleServer().setAddress("redis://127.0.0.1:6379").  setDatabase(0);
        this.redisson = Redisson.create(config);
//        RSetCache<String> userIds = getSetCache("recently_updated_balance");
//        userIds.add("sasha1",1, TimeUnit.HOURS);

    }


    public <V> RSetCache<V> getSetCache(String category) {
        return redisson.getSetCache(category);
    }

}
