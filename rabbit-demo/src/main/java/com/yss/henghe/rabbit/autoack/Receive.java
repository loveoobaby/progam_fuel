package com.yss.henghe.rabbit.autoack;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receive {

    private final static String QUEUE_NAME = "lixingjun";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("henghe-125");
        factory.setUsername("admin");
        factory.setPassword("admin");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            try {
                System.out.println(new String(delivery.getBody()));
                if(delivery.getEnvelope().getDeliveryTag()%2 == 0){
                    throw new RuntimeException("");
                }else {
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                }
            }catch (Exception e){
                channel.basicNack(delivery.getEnvelope().getDeliveryTag(), false, false);
            }

        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> {
        });

    }
}
