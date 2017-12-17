//import org.apache.activemq.ActiveMQConnectionFactory;
//import org.junit.Test;
//
//import javax.jms.*;
//
///**
// * Created by Administrator on 2017/11/29.
// */
//public class TopicTest {
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
//        Topic topic = session.createTopic("xiaojidunmogu");
//        MessageProducer producer = session.createProducer(topic);
//        ObjectMessage objectMessage = session.createObjectMessage();
//        objectMessage.setObject(new Person("zhangsanfeng",18));;
//
//        producer.send(objectMessage);
//        session.close();
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
//        Topic topic = session.createTopic("xiaojidunmogu");
//        MessageConsumer consumer = session.createConsumer(topic);
//        consumer.setMessageListener(new MessageListener() {
//            @Override
//            public void onMessage(Message message) {
//                ObjectMessage objectMessage= (ObjectMessage) message;
//                Person person= null;
//                try {
//                    person = (Person) objectMessage.getObject();
//                    System.out.println(person.getName()+"-----"+person.getAge());
//                } catch (JMSException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        System.in.read();
//    }
//}
