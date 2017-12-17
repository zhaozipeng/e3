package com.e3.utils;

import java.util.List;
import java.util.Set;

/**
 * Created by zhiyuan on 2017/11/24.
 */
public interface JedisUtils {


    public boolean exist(String key);
    public void hdel(String key, String field);

    public Set<String> hkeys(String key);
    //提供常用的方法的调用
    /**
     * set get
     */
    public void set(String key, String value);

    /**
     * 根据key获取value
     * @param key
     * @return
     */
    public String get(String key);

    /**
     * hset
     * @param key
     * @param field
     * @param value
     */
    public void hset(String key, String field, String value);

    public String hget(String key, String field);

    public void del(String key);
    /**
     * 根据key获取value的集合
     * @param key
     * @return
     */
    public List<String> hvals(String key);

    /**
     * 递增
     */
    public Long incr(String key);

    /**
     * 递减
     * @param key
     * @return
     */
    public Long decr(String key);
    /**
     * 设置过期时间
     */
    public void expire(String key, int time);

    public void pexpire(String key, long time);

    /**
     * 获取当前剩余时间 s
     * @param key
     * @return
     */
    public long ttl(String key);

    /**
     * 获取当前剩余的ms
     * @param key
     * @return
     */
    public long pttl(String key);
    /**
     * 判断当前的key是否存在
     */
    public boolean exists(String key);







}
