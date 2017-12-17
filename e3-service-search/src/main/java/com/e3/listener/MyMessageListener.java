package com.e3.listener;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

/**
 * Created by Administrator on 2017/11/29.
 */
public class MyMessageListener implements MessageListener{
    @Override
    public void onMessage(Message message) {
        MapMessage mapMessage= (MapMessage) message;
        String name=null;

        try {
            name= mapMessage.getString("name");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println(name);
    }
}
