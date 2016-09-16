package amqp.client;

import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

/**
 * 使用rabbitmq的Publisher Confirms机制,去避免发送的消息丢失
 * 和ack机制类似,如果发送失败,则重新发
 * Created by youzhihao on 16/9/16.
 */
public class ConfirmDontLoseMessages {

    static int msgCount = 10000;

    final static String QUEUE_NAME = "confirm-test";

    static ConnectionFactory connectionFactory;

    public static void main(String[] args)
            throws IOException, InterruptedException {
        if (args.length > 0) {
            msgCount = Integer.parseInt(args[0]);
        }

        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("127.0.0.1");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("yzh");
        connectionFactory.setPassword("yzh");
        connectionFactory.setConnectionTimeout(2000);

        // Consume msgCount messages.
        (new Thread(new Consumer())).start();
        // Publish msgCount messages and wait for confirms.
        (new Thread(new Publisher())).start();
    }

    @SuppressWarnings("ThrowablePrintedToSystemOut")
    static class Publisher implements Runnable {

        public void run() {
            try {
                long startTime = System.currentTimeMillis();

                // Setup
                Connection conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);
                //保证消息发送的可靠性
                ch.confirmSelect();
                // Publish
                for (long i = 0; i < msgCount; ++i) {
                    ch.basicPublish("", QUEUE_NAME,
                            MessageProperties.PERSISTENT_BASIC,
                            "nop".getBytes());
                }

                ch.waitForConfirms();
                System.out.println("发送确认完毕");
                ch.close();
                conn.close();

                long endTime = System.currentTimeMillis();
                System.out.printf("Test took %.3fs\n",
                        (float) (endTime - startTime) / 1000);
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
                conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Consume
                QueueingConsumer qc = new QueueingConsumer(ch);
                ch.basicConsume(QUEUE_NAME, true, qc);
                while (true) {
                    qc.nextDelivery();
                    System.out.println(msgCount);
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
