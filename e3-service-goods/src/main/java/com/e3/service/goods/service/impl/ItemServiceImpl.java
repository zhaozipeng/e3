package com.e3.service.goods.service.impl;

import com.e3.service.goods.mapper.*;
import com.e3.service.goods.pojo.*;
import com.e3.service.goods.service.ItemService;
import com.e3.utils.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemServiceImpl implements ItemService{
    private static String ITEM_BASE = "ITEM_BASE";
    private static String ITEM_DESC = "ITEM_DESC";
    private static String ITEM = "ITEM";
    @Resource
    TbItemMapper tbItemMapper;
    @Resource
    TbItemCatMapper tbItemCatMapper;

    @Resource
    TbItemDescMapper tbItemDescMapper;
    @Resource
    TbItemParamMapper itemParamMapper;
    @Resource
    TbItemParamItemMapper tbItemParamItemMapper;

    @Resource
    JmsTemplate jmsTemplate;

    @Resource(name="topicDestination")
    Destination destination;
    @Resource
    JedisUtils jedisUtils;

    public TbItem selectTbItemById(Long id) {

        return tbItemMapper.selectByPrimaryKey(id);
    }
    @Override
    public EasyUIDataResult selectTbItem(Integer page, Integer rows) {
        //执行PageHelper
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = tbItemMapper.selectByExample(new TbItemExample());
//        select * from tbItem limit 0,30
        PageInfo<TbItem> pageInfo=new PageInfo<TbItem>(tbItems);
        //封装一个EasyDataResult
        EasyUIDataResult<TbItem> tbItemEasyUIDataResult = new EasyUIDataResult<>();
        //获取总条目的值
        tbItemEasyUIDataResult.setTotal((int) pageInfo.getTotal());
        tbItemEasyUIDataResult.setRows(tbItems);

        return tbItemEasyUIDataResult;
    }
    //根据父类id查询子节点
    @Override
    public List<TbItemCat> selectItemCatList(Long id) {


        TbItemCatExample example=new TbItemCatExample();
        example.createCriteria().andParentIdEqualTo(id);
        return tbItemCatMapper.selectByExample(example);
    }

    @Override
    public E3Result saveItem(TbItem tbItem, String desc,String paramData) {
        //存储到数据库
        //补全id createTime updateTime
        long id = IDUtils.genItemId();
        tbItem.setId(id);
        tbItem.setCreated(new Date());
        tbItem.setUpdated(new Date());
        //上架 1  下架 2
        tbItem.setStatus((byte) 1);
        //将商品添加到数据库
        tbItemMapper.insert(tbItem);
        //将商品详情信息添加到数据库
        E3Result e3Result= saveItemDesc(id,desc);
        saveItemParam(id, paramData);
        //发送消息
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //1种把整个商品发送过去
                //2种发送商品id
                MapMessage mapMessage=session.createMapMessage();
                mapMessage.setString("handler","add");
                mapMessage.setString("item_id",id+"");

                return mapMessage;
            }
        });


        return e3Result;
    }

    @Override
    public TbItem selectItemById(Long id) {
        //查询MySQL数据库---公司里边:业务实现,接口测试,优化
        //二级缓存
        //先从redis中取
        //定义redis层级结构 Item:ItemBase:id
        TbItem tbItem = null;
        String s = jedisUtils.get(ITEM + ":" + ITEM_BASE + ":" + id);
        if (TextUtils.isEmpty(s)) {
            tbItem = tbItemMapper.selectByPrimaryKey(id);
            jedisUtils.set(ITEM + ":" + ITEM_BASE + ":" + id, JsonUtils.objectToJson(tbItem));
            jedisUtils.expire(ITEM + ":" + ITEM_BASE + ":" + id, 24 * 3600);
        } else {
            tbItem = JsonUtils.jsonToPojo(s, TbItem.class);
        }
        return tbItem;
    }

    @Override
    public TbItemDesc selectItemDescById(Long id) {
        //Item:ItemDesc:id
        TbItemDesc tbItemDesc = null;
        String s = jedisUtils.get(ITEM + ":" + ITEM_DESC + ":" + id);
        if (TextUtils.isEmpty(s)) {
            tbItemDesc= tbItemDescMapper.selectByPrimaryKey(id);
            jedisUtils.set(ITEM + ":" + ITEM_DESC + ":" + id, JsonUtils.objectToJson(tbItemDesc));
            jedisUtils.expire(ITEM + ":" + ITEM_BASE + ":" + id, 24 * 3600);
        }else{
            tbItemDesc=  JsonUtils.jsonToPojo(s, TbItemDesc.class);
        }
        return tbItemDesc;
    }

    /**
     * 添加商品模板信息
     *
     * @param id        商品id
     * @param
     */
    private void saveItemParam(long id, String itemParams) {
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(id);
        tbItemParamItem.setParamData(itemParams);
        tbItemParamItem.setUpdated(new Date());
        tbItemParamItem.setCreated(new Date());
        //添加到数据库
        tbItemParamItemMapper.insert(tbItemParamItem);
    }

    @Override
    public EasyUIDataResult selectParamList(Integer page, Integer rows) {
      PageHelper.startPage(page,rows);
      //查询如果有大文本的话用
        TbItemParamExample example=new TbItemParamExample();
         List<TbItemParam>itemParams=itemParamMapper.selectByExampleWithBLOBs(example);
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(itemParams);
        EasyUIDataResult<TbItemParam>tbItemParamEasyUIDataResult=new EasyUIDataResult<>();
        tbItemParamEasyUIDataResult.setTotal((int) pageInfo.getTotal());
        tbItemParamEasyUIDataResult.setRows(itemParams);

        return tbItemParamEasyUIDataResult;
    }

    @Override
    public E3Result selectParamByCatId(Long id) {

        TbItemParamExample example=new TbItemParamExample();
        example.createCriteria().andItemCatIdEqualTo(id);
        List<TbItemParam>list=itemParamMapper.selectByExampleWithBLOBs(example);
        //从集合中获取第0个
        if(list.size()>0&&list!=null){
            TbItemParam tbItemParam=list.get(0);
            return E3Result.ok(tbItemParam);
        }
        return new E3Result();
    }

    @Override
    public E3Result saveParam(Long id, String paramData) {
        TbItemParam tbItemParam=new TbItemParam();

        tbItemParam.setItemCatId(id);
        tbItemParam.setParamData(paramData);
        tbItemParam.setCreated(new Date());
        tbItemParam.setUpdated(new Date());
        itemParamMapper.insert(tbItemParam);


        return E3Result.ok(null);
    }

    private E3Result saveItemDesc(long id, String desc) {
        TbItemDesc tbItemDesc=new TbItemDesc();
        tbItemDesc.setItemId(id);
        tbItemDesc.setItemDesc(desc);
        tbItemDesc.setCreated(new Date());
        tbItemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(tbItemDesc);
        return  E3Result.ok(null);
    }
}
