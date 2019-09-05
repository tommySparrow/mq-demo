package com.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息消费方
 *
 * @author User
 */
public class TopicNoPersistenceReceiver {

    public static void main(String[] args) throws Exception {

        //1.创建一个连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.190.129:61616");
        //2.创建一个连接
        Connection connection = connectionFactory.createConnection();
        //接收消息之前，需要把连接启动一下
        connection.start();
        //3.创建一个Session
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        //4.创建一个目的地
        Destination destination = session.createTopic("myTopic");
        //5.创建一个消息的消费者（接收者）

        MessageConsumer messageConsumer = session.createConsumer(destination);

        //创建消费的监听
        messageConsumer.setMessageListener(new MessageListener() {

            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("获取消息：" + textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
