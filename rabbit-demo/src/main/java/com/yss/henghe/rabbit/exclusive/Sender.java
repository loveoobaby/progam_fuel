package com.yss.henghe.rabbit.exclusive;

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

        channel.queueDeclare(QUEUE_NAME, true, true, false, null);

        for(int i=0;i<10;i++){
            String message = "" + System.currentTimeMillis();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        }

    }

}
