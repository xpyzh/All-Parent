package amqp.client;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 使用rabbitmq的Publisher Confirms机制,去避免发送的消息丢失
 * 和ack机制类似,如果发送失败,则重新发
 * Created by youzhihao on 16/9/16.
 */
public class ConfirmDontLoseMessages {

    static int msgCount = 1000000;

    final static String QUEUE_NAME = "confirm-test";

    static ConnectionFactory connectionFactory1;
    static ConnectionFactory connectionFactory2;


    public static void main(String[] args)
            throws IOException, InterruptedException {
        if (args.length > 0) {
            msgCount = Integer.parseInt(args[0]);
        }

        //生产
        connectionFactory1 = new ConnectionFactory();
        connectionFactory1.setHost("127.0.0.1");
        connectionFactory1.setPort(5673);
        connectionFactory1.setVirtualHost("rabbitmq-single");
        connectionFactory1.setUsername("root");
        connectionFactory1.setPassword("root");
        connectionFactory1.setConnectionTimeout(2000);
        //消费
        connectionFactory2 = new ConnectionFactory();
        connectionFactory2.setHost("127.0.0.1");
        connectionFactory2.setPort(5672);
        connectionFactory2.setVirtualHost("rabbitmq-single");
        connectionFactory2.setUsername("root");
        connectionFactory2.setPassword("root");
        connectionFactory2.setConnectionTimeout(2000);

        // Publish msgCount messages and wait for confirms.
        (new Thread(new Publisher(), "publisher")).start();
        // Consume msgCount messages.
        (new Thread(new Consumer(), "consumer")).start();

    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    static class Publisher implements Runnable {

        public void run() {
            try {
                long startTime = System.currentTimeMillis();

                // Setup
                Connection conn = connectionFactory1.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);
                //保证消息发送的可靠性
                ch.confirmSelect();
                // Publish
                for (long i = 0; i < 100; ++i) {
                    ch.basicPublish("", QUEUE_NAME,
                            MessageProperties.PERSISTENT_BASIC,
                            "nop".getBytes());
                }

                ch.waitForConfirms();
                System.out.println("发送确认完毕");
                ch.close();
                conn.close();
                long endTime = System.currentTimeMillis();
                System.out.printf("Test took %.3fs\n", (float) (endTime - startTime) / 1000);
            } catch (Throwable e) {
                System.out.println("foobar :(");
                System.out.print(e);
            }
        }
    }

    static class Consumer implements Runnable {

        public void run() {
            Connection conn = null;
            try {
                // Setup
                conn = connectionFactory2.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Consume
                QueueingConsumer qc = new QueueingConsumer(ch);
                ch.basicConsume(QUEUE_NAME, true, qc);
                while (true) {
                    QueueingConsumer.Delivery delivery = qc.nextDelivery();
                    System.out.println(new String(delivery.getBody()));
                }
                // Cleanup
            } catch (Throwable e) {
                System.out.println("Whoosh!");
                System.out.print(e);
                try {
                    if (conn != null)
                        conn.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}
