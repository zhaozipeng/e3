package com.e3.listener;

import com.e3.service.search.service.SearchService;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by zhiyuan on 2017/11/29.
 */
public class UpdateSolrListener implements MessageListener {

    @Resource
    SearchService searchService;
    @Override
    public void onMessage(Message message) {
        //1 取出消息
        MapMessage mapMessage= (MapMessage) message;
        try {
            String handler = mapMessage.getString("handler");
            switch (handler){
                case "add":
                    Long item_id =mapMessage.getLong("item_id");
                    //查询出商品信息
                    searchService.updateSolr(item_id);
                    System.out.println("--------更新索引库------");
                    break;
                case "delete":
                    break;

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
