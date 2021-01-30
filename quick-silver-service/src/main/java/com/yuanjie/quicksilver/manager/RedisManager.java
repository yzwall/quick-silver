package com.yuanjie.quicksilver.manager;

import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author yuanjie(qinhua)
 * @date 2021/01/21
 */
@Component
public class RedisManager {

    private static Map<String, Serializable> cache = Maps.newConcurrentMap();

    public boolean setex(String key, Serializable value, Integer expireSeconds) {
        cache.put(key, value);
        return true;
    }

    public <T> Optional<T> get(String key, Class<T> clazz) {

        //return Optional.ofNullable(key).map(cache::get);
        return Optional.empty();
    }
}
