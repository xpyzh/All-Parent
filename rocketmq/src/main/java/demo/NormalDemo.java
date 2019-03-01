package demo;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by youzhihao on 2018/6/7.
 */
public class NormalDemo {

    private static final String NAMESRV = "127.0.0.1:9876";

    private static final String TOPIC = "test";

    private static final String PRODUCER_GROUP = "normal-producer-group";

    private static final String CONSUMER_GROUP = "normal-consumer-group";

    public static void main(String[] args) throws Exception {
        //new Producer().start();
        new Consumer().start();
    }

    static class Producer extends Thread {

        private final AtomicInteger count = new AtomicInteger(1);

        private final DefaultMQProducer producer;

        public Producer() {
            producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(NAMESRV);
            try {
                producer.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            while (true) {
                try {
                    Message msg = new Message(TOPIC, ("Hello RocketMQ " + count.getAndIncrement()).getBytes(RemotingHelper.DEFAULT_CHARSET));
                    //Call send message to deliver message to one of brokers.
                    SendResult sendResult = producer.send(msg);
                    System.out.println("produce: " + JSONObject.toJSON(sendResult));
                    Thread.sleep(1000);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    static class Consumer {

        private final DefaultMQPushConsumer consumer;

        public Consumer() {
            consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
            consumer.setNamesrvAddr(NAMESRV);
            try {
                consumer.subscribe(TOPIC, "*");
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        }

        public void start() {
            try {
                consumer.registerMessageListener(new MessageListenerConcurrently() {
                    @Override
                    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                        for (MessageExt messageExt : msgs) {
                            System.out.println("consume: " + new String(messageExt.getBody()));
                        }
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
                });
                consumer.start();
            } catch (MQClientException e) {
                e.printStackTrace();
            }
        }
    }
}
