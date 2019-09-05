package com.quene;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 消息消费方
 * @author User
 */
public class MqReceiver {

    public static void main(String[] args) throws Exception {

        //1.创建一个连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.190.129:61616");
        //2.创建一个连接
        Connection connection = connectionFactory.createConnection();
        //3.创建一个Session
        Session session = connection.createSession(Boolean.FALSE, Session.CLIENT_ACKNOWLEDGE);
        //4.创建一个目的地
        Destination destination = session.createQueue("myQueue");
        //5.创建一个消息的消费者（接收者）

        MessageConsumer messageConsumer = session.createConsumer(destination);

        //接收消息之前，需要把连接启动一下
        connection.start();

        //6.接收消息 同步接收
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                String text = null;
                try {
                    text = ((TextMessage) message).getText();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println("接收到的消息内容是：" + text);
            }
        });
    }
}
