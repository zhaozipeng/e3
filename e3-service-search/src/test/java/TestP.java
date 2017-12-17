//import org.apache.xbean.spring.context.ClassPathXmlApplicationContext;
//import org.junit.Test;
//import org.springframework.context.ApplicationContext;
//import org.springframework.jms.core.JmsTemplate;
//import org.springframework.jms.core.MessageCreator;
//
//
//import javax.jms.*;
//import java.io.IOException;
//
///**
// * Created by Administrator on 2017/11/29.
// */
//public class TestP {
//
//    @Test
//    public void test1(){
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-activemq-producer.xml");
//        JmsTemplate jmsTemplate = (JmsTemplate) applicationContext.getBean("jmsTemplate");
//        Queue queue= (Queue) applicationContext.getBean("queueDestination");
//        jmsTemplate.send(queue, new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                MapMessage mapMessage=session.createMapMessage();
//                mapMessage.setString("name","11111");
//
//                return mapMessage;
//            }
//        });
//        }
//        @Test
//    public void test2(){
//
//            ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/spring-activemq-consumer.xml");
//            try {
//                System.in.read();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//}
