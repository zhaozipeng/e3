package com.e3.service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;


/**
 * Created by zhiyuan on 2017/11/17.
 */
public class OrdercartProvider {
    //打log
    private static final Logger LOGGER = LoggerFactory.getLogger(OrdercartProvider.class);
    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            context.start();
        } catch (Exception e) {
            LOGGER.error("== DubboProvider context start error:",e);
        }
        //线程锁
        synchronized (OrdercartProvider.class) {
            while (true) {
                try {
                    OrdercartProvider.class.wait();
                    System.out.println("商品服务已启动成功！");
                } catch (InterruptedException e) {
                    LOGGER.error("== synchronized error:",e);
                }
            }
        }
    }
}
