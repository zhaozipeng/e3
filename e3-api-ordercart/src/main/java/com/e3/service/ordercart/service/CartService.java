package com.e3.service.ordercart.service;

import com.e3.service.goods.pojo.TbItem;

import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */
public interface CartService {
    void addRedisCart(Long itemId, Integer num, Long id);
    public List<TbItem> getRedisCart(Long id);
}
