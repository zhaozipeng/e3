//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//
//import javax.jms.*;
//import java.io.IOException;
//
///**
// * Created by Administrator on 2017/11/29.
// */
//public class QueueTest {
//    @Test
//    public void testProducer() throws Exception {
//
//        //1 创建atctivemq的链接工厂
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.17.61.58:61616");
//        //2通过链接工厂获取链接
//        Connection connection = connectionFactory.createConnection();
//        //3开启一个发送者
//        connection.start();
//        //4创建一个回话
//        /**
//         * 第一个参数是true或者fals
//         * 是否开启事务
//         *第二个参数是是否自动启动还是手动启动
//         *
//         */
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //5指定消息是点对点还是订阅方式  指定消息的编号（暗号）;
//        Queue queue = session.createQueue("天王盖地虎");
//        //6通过回话指定是发送者还是生产者
//        MessageProducer messageProducer = session.createProducer(queue);
//        //7创建消息  指定消息类型
//        TextMessage textMessage = session.createTextMessage();
//        textMessage.setText("中午吃大餐");
//
//        //8发送消息
//        messageProducer.send(textMessage);
//        //9关闭资源
//        session.close();
//        messageProducer.close();
//        connection.close();
//    }
//    @Test
//    public void testConsumer() throws Exception {
//        //1 创建atctivemq的链接工厂
//        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://172.17.61.58:61616");
//        //2通过链接工厂获取链接
//        Connection connection = connectionFactory.createConnection();
//        //3开启一个发送者
//        connection.start();
//        //4创建一个回话
//        /**
//         * 第一个参数是true或者fals
//         * 是否开启事务
//         *第二个参数是是否自动启动还是手动启动
//         *
//         */
//        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        //5指定消息是点对点还是订阅方式  指定消息的编号（暗号）;
//        Queue queue = session.createQueue("天王盖地虎");
//        //6创建消费者
//        MessageConsumer consumer = session.createConsumer(queue);
//        //
//        consumer.setMessageListener(new MessageListener() {
//            //接受到消息
//            @Override
//            public void onMessage(Message message) {
//                TextMessage textMessage= (TextMessage) message;
//                try {
//                    String text = textMessage.getText();
//                    System.out.print(text);
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        System.in.read();
//    }
//}
