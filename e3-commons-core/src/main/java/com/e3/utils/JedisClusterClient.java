package com.e3.utils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by zhiyuan on 2017/11/24.
 */
public class JedisClusterClient implements JedisUtils {
    @Resource
    JedisCluster jedisCluster;

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    @Override
    public boolean exist(String key) {
        return jedisCluster.exists(key);
    }

    @Override
    public void hdel(String key, String field) {
        jedisCluster.hdel(key,field);
    }

    @Override
    public Set<String> hkeys(String key) {
        return jedisCluster.hkeys(key);
    }

    @Override
    public void set(String key, String value) {
         jedisCluster.set(key, value);
    }

    @Override
    public String get(String key) {
        // TODO Auto-generated method stub
        return jedisCluster.get(key);
    }

    @Override
    public void hset(String key, String field, String value) {
        jedisCluster.hset(key,field,value);
    }

    @Override
    public Long incr(String key) {
        // TODO Auto-generated method stub
        return jedisCluster.incr(key);
    }

    @Override
    public Long decr(String key) {
        // TODO Auto-generated method stub
        return jedisCluster.decr(key);
    }

    @Override
    public void expire(String key, int time) {
        jedisCluster.expire(key,time);
    }

    @Override
    public void pexpire(String key, long time) {
jedisCluster.pexpire(key,time);
    }

    @Override
    public long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public long pttl(String key) {
        return jedisCluster.pttl(key);
    }

    @Override
    public boolean exists(String key) {
        return jedisCluster.exists(key);
    }


    @Override
    public String hget(String key, String field) {
        // TODO Auto-generated method stub
        return jedisCluster.hget(key, field);
    }

    @Override
    public void del(String key) {
        jedisCluster.del(key);
    }

    @Override
    public List<String> hvals(String key) {
        return jedisCluster.hvals(key);
    }


}
