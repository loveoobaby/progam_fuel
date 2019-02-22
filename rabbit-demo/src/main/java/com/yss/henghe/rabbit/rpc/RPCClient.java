package com.yss.henghe.rabbit.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable {

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String rpcResponseQueue = "rpc_response";
    private final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setHost("henghe-125");

        connection = factory.newConnection();
        channel = connection.createChannel();
    }

    public void init(){
        try {
            channel.queueDeclare(rpcResponseQueue, true, false, false, null);
            channel.basicConsume(rpcResponseQueue, true, (consumerTag, delivery) -> {
                response.offer(new String(delivery.getBody(), "UTF-8"));
            }, consumerTag -> {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] argv) {
        try (RPCClient fibonacciRpc = new RPCClient()) {
            fibonacciRpc.init();
            long init = System.currentTimeMillis();
            for (int i = 0; i < 10000; i++) {
                long start = System.currentTimeMillis();
                String i_str = Integer.toString(8);
//                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = fibonacciRpc.call(i_str);
//                System.out.println(" [.] Got '" + response + "' " + (System.currentTimeMillis() - start));
//                Thread.sleep(20*1000);
//                System.out.println(i);
            }
            System.out.println(" [.] Got '" + "' " + (System.currentTimeMillis() - init));
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String call(String message) throws IOException, InterruptedException {
        final String corrId = UUID.randomUUID().toString();

        AMQP.BasicProperties props = new AMQP.BasicProperties
                .Builder()
                .correlationId(corrId)
                .replyTo(rpcResponseQueue)
                .build();

        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
        String result = response.take();
        return result;
    }

    public void close() throws IOException {
        connection.close();
    }
}
