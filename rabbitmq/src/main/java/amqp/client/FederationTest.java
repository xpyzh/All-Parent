package amqp.client;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.text.MessageFormat;

/**
 * Created by youzhihao on 2018/7/30.
 */
public class FederationTest {

    final static String QUEUE_NAME = "test";
    final static String EXCHANGE_NAME = "test_exchange";

    public static void main(String[] args) {
        //(new Thread(new Publisher(5672, 1), "publisher")).start();
        //(new Thread(new Publisher(5673, 1), "publisher")).start();
        // Consume msgCount messages.
        //(new Thread(new Consumer(5672, 0), "consumer")).start();
        //(new Thread(new Consumer(5673, 1), "consumer")).start();
    }


    @SuppressWarnings("ThrowablePrintedToSystemOut")
    static class Publisher implements Runnable {
        private int sleep;

        private ConnectionFactory connectionFactory;

        public Publisher(int port, int sleep) {
            this.sleep = sleep;
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(port);
            connectionFactory.setVirtualHost("rabbitmq-single");
            connectionFactory.setUsername("root");
            connectionFactory.setPassword("root");
            connectionFactory.setConnectionTimeout(2000);
        }

        public void run() {
            Connection conn = null;
            try {
                conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);
                ch.basicQos(1);
                while (true) {
                    String message = MessageFormat.format("message from by {0}", String.valueOf(connectionFactory.getPort()));
                    System.out.println("端口" + connectionFactory.getPort() + "发送消息消息:" + message);
                    ch.basicPublish(EXCHANGE_NAME, QUEUE_NAME,
                            MessageProperties.PERSISTENT_BASIC,
                            message.getBytes());
                    Thread.currentThread().sleep(this.sleep * 1000);
                }
            } catch (Throwable e) {
                System.out.println("foobar :(");
                System.out.print(e);
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class Consumer implements Runnable {

        private ConnectionFactory connectionFactory;

        private final int sleep;


        public Consumer(int port, int sleep) {
            this.sleep = sleep;
            connectionFactory = new ConnectionFactory();
            connectionFactory.setHost("127.0.0.1");
            connectionFactory.setPort(port);
            connectionFactory.setVirtualHost("rabbitmq-single");
            connectionFactory.setUsername("root");
            connectionFactory.setPassword("root");
            connectionFactory.setConnectionTimeout(2000);
        }

        public void run() {
            Connection conn = null;
            try {
                // Setup
                conn = connectionFactory.newConnection();
                Channel ch = conn.createChannel();
                ch.queueDeclare(QUEUE_NAME, true, false, false, null);

                // Consume
                ch.basicQos(1);
                ch.basicConsume(QUEUE_NAME, false, new com.rabbitmq.client.Consumer() {
                    @Override
                    public void handleConsumeOk(String consumerTag) {

                    }

                    @Override
                    public void handleCancelOk(String consumerTag) {

                    }

                    @Override
                    public void handleCancel(String consumerTag) throws IOException {

                    }

                    @Override
                    public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {

                    }

                    @Override
                    public void handleRecoverOk(String consumerTag) {

                    }

                    @Override
                    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                        System.out.println("端口" + connectionFactory.getPort() + "接收消息:" + new String(body));
                        ch.basicAck(envelope.getDeliveryTag(), false);
                        try {
                            Thread.currentThread().sleep(sleep * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
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
