package com.e3.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

/**
 * Created by zhiyuan on 2017/11/24.
 */
public class JedisSingleClient implements JedisUtils {
    //JedisPool连接池  Spring DI IOC 控制反转
    JedisPool jedisPool;

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public boolean exist(String key) {
        Jedis jedis = jedisPool.getResource();

        jedis.close();
        return jedis.exists(key);
    }

    @Override
    public void hdel(String key, String field) {
        Jedis jedis = jedisPool.getResource();

        jedis.hdel(key,field);
        jedis.close();
    }

    @Override
    public Set<String> hkeys(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.close();
        return jedis.hkeys(key);
    }

    @Override
    public void set(String key, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.set(key, value);
        jedis.close();;
    }

    @Override
    public String get(String key) {
        Jedis jedis = jedisPool.getResource();
        String s = jedis.get(key);
        jedis.close();
        return s;
    }

    @Override
    public void hset(String key, String field, String value) {
        Jedis jedis = jedisPool.getResource();
        jedis.hset(key,field,value);
        jedis.close();
    }

    @Override
    public String hget(String key, String field) {
        Jedis jedis = jedisPool.getResource();
        String hget = jedis.hget(key, field);
        jedis.close();
        return hget;
    }

    @Override
    public void del(String key) {
        Jedis jedis = jedisPool.getResource();
        jedis.del(key);
        jedis.close();
    }


    @Override
    public List<String> hvals(String key) {
        Jedis jedis = jedisPool.getResource();
        List<String> hvals = jedis.hvals(key);
        jedis.close();
        return hvals;
    }

    @Override
    public Long incr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long incr = jedis.incr(key);
        jedis.close();
        return incr;
    }

    @Override
    public Long decr(String key) {
        Jedis jedis = jedisPool.getResource();
        Long incr = jedis.decr(key);
        jedis.close();
        return incr;
    }

    @Override
    public void expire(String key, int time) {
        Jedis jedis = jedisPool.getResource();
        jedis.expire(key,time);
        jedis.close();

    }

    @Override
    public void pexpire(String key, long time) {
        Jedis jedis = jedisPool.getResource();
        jedis.pexpire(key,time);
        jedis.close();
    }

    @Override
    public long ttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long ttl = jedis.ttl(key);
        jedis.close();
        return ttl;
    }

    @Override
    public long pttl(String key) {
        Jedis jedis = jedisPool.getResource();
        Long pttl = jedis.pttl(key);
        jedis.close();
        return pttl;
    }

    @Override
    public boolean exists(String key) {
        Jedis jedis = jedisPool.getResource();
        Boolean exists = jedis.exists(key);
        jedis.close();
        return exists;
    }
}
