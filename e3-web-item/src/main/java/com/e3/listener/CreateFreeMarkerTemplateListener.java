package com.e3.listener;

import com.e3.service.goods.pojo.TbItem;
import com.e3.service.goods.pojo.TbItemDesc;
import com.e3.service.goods.service.ItemService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class CreateFreeMarkerTemplateListener implements MessageListener {
    @Resource
    ItemService itemService;
    @Resource
    FreeMarkerConfigurer freeMarkerConfigurer;
    @Override
    public void onMessage(Message message) {
        //1 取出消息
        MapMessage mapMessage = (MapMessage) message;
        try {
            String handler = mapMessage.getString("handler");
            switch (handler) {
                case "add":
                    Long item_id = mapMessage.getLong("item_id");
                    //查询出商品信息 --生成静态html页面
                    TbItem item = itemService.selectItemById(item_id);
                    TbItemDesc itemDesc = itemService.selectItemDescById(item_id);
                    Configuration configuration = freeMarkerConfigurer.getConfiguration();

                    try {
                        Template template = configuration.getTemplate("item.ftl");
                        //准备数据
                        Map<String,Object> map=new HashMap<String,Object>();
                        map.put("item",item);
                        map.put("itemDesc",itemDesc);
                        FileWriter fileWriter=new FileWriter(new File("c:/item/item/" + item_id + ".html"));
                        //生成html
                        template.process(map,fileWriter);
                        fileWriter.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
                case "delete":
                    break;

            }
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
