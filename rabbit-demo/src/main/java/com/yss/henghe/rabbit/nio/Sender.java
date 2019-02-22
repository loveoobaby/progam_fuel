package com.yss.henghe.rabbit.nio;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.nio.NioParams;

public class Sender {

    private static final String EXCHANGE_NAME = "logs-topic";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("henghe-125");
        factory.useNio();
        factory.setRequestedHeartbeat(60);

        factory.setAutomaticRecoveryEnabled(true);
//        factory.setNetworkRecoveryInterval();
//        System.out.println(factory.getNetworkRecoveryInterval());

        factory.setNioParams(new NioParams().setNbIoThreads(4));


        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");

            String message = argv.length < 1 ? "info: Hello World!" :
                    String.join(" ", argv);
            for (int i =0; i<100000; i++) {
                try {
                    channel.basicPublish(EXCHANGE_NAME, "log.error", null, message.getBytes("UTF-8"));
                    System.out.println(" [x] Sent '" + i + "'");
                }catch (Exception e){
                    e.printStackTrace();
                    Thread.sleep(1000);
                }

            }


        }
        Thread.sleep(100000*1000);
    }

}
