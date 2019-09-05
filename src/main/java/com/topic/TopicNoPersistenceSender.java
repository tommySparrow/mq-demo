package com.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息发送方
 * @author User
 */
public class TopicNoPersistenceSender {

    public static void main(String[] args) throws Exception {

        //1.创建一个连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.190.129:61616");
        //2.创建一个连接
        Connection connection = connectionFactory.createConnection();
        //3.创建一个Session
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        //5.创建一个目的地
        Destination destination = session.createTopic("myTopic");
        //6.创建一个消息的生产者（发送者）
        MessageProducer messageProducer = session.createProducer(destination);

        //设置发送的消息是否需要持久化
        //这里使用不持久化
        messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i = 1; i <= 10; i++) {
            //4.创建消息，此处创建了一个文本消息
            TextMessage message = session.createTextMessage("队列--" + i);
            //7.发送消息
            messageProducer.send(message);
            //在本地打印消息
            System.out.println("我现在发的消息是：" + message.getText());
        }

        //关闭连接
        messageProducer.close();
        session.close();
        connection.close();
    }
}
