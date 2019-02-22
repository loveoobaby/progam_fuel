package com.yss.henghe.rabbit.basic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Sender {

    private final static String QUEUE_NAME = "lixingjun";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("henghe-125");

        Connection conn = factory.newConnection();
        Channel channel = conn.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        for(int i=0;i<100000;i++){
            String message = "" + System.currentTimeMillis();
            // basicPublish是同步方法，可能阻塞，但并不保证消息一定可以到达MQ，并正确持久化
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(i);
        }

        channel.close();
        conn.close();

    }

}
