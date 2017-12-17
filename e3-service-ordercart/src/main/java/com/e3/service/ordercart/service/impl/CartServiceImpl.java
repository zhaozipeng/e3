package com.e3.service.ordercart.service.impl;

import com.e3.service.goods.mapper.TbItemMapper;
import com.e3.service.goods.pojo.TbItem;
import com.e3.service.ordercart.service.CartService;
import com.e3.utils.JedisUtils;
import com.e3.utils.JsonUtils;
import com.e3.utils.TextUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/4.
 */
@Service
public class CartServiceImpl implements CartService{
    private static final String E3_CART="E3_CART";
    @Resource
    JedisUtils jedisUtils;
    @Resource
    TbItemMapper tbItemMapper;

    @Override
    public void addRedisCart(Long itemId, Integer num, Long id) {
    //获取这条商品是否存在
        TbItem tbItem=null;
        String hget = jedisUtils.hget(E3_CART + ":" + id, itemId + "");
        if(TextUtils.isEmpty(hget)){
           tbItem = tbItemMapper.selectByPrimaryKey(itemId);
            //添加一条
            tbItem.setNum(num);
        }else {
            //将字符串转化成对象
            tbItem = JsonUtils.jsonToPojo(hget
                    , TbItem.class);
            tbItem.setNum(tbItem.getNum() + num);

        }

        jedisUtils.hset(E3_CART + ":" + id, itemId + "", JsonUtils.objectToJson(tbItem));
        List<TbItem> tbitemList= getRedisCart(id);
    }

    public List<TbItem> getRedisCart(Long id) {
        List<String> hvals = jedisUtils.hvals("E3_CART" + id);
        //cookie将所有信息弄成一个集合将集合转成json串
        //redis 一条一条村，key是用户id
        List<TbItem>itemList=new ArrayList<>();
        for (String s:hvals
             ) {
            TbItem tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
            itemList.add(tbItem);

        }
        return itemList;
    }
}
