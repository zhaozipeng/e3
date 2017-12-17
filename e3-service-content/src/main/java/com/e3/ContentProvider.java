package com.e3;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;


/**
 * Created by zhiyuan on 2017/11/17.
 */
public class ContentProvider {
    //打log
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentProvider.class);
    public static void main(String[] args) {
        try {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/spring-context.xml");
            context.start();
        } catch (Exception e) {
            LOGGER.error("== DubboProvider context start error:",e);
        }
        //线程锁
        synchronized (ContentProvider.class) {
            while (true) {
                try {
                    ContentProvider.class.wait();
                    System.out.println("商品服务已启动成功！");
                } catch (InterruptedException e) {
                    LOGGER.error("== synchronized error:",e);
                }
            }
        }
    }
}
