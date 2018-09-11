package demo;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.util.Date;
import java.util.concurrent.Executors;

/**
 * Created by youzhihao on 2018/9/6.
 * 异步发送消息
 */
public class AsyncProducer {

    public static void main(String[] args) {

        initProducer();

    }

    public static void initProducer() {
        try {
            String groupName = "test";
            final DefaultMQProducer producer = new DefaultMQProducer(groupName);
            producer.setNamesrvAddr("127.0.0.1:9876");
            producer.setRetryTimesWhenSendAsyncFailed(3);
            producer.start();
            Executors.newFixedThreadPool(1).submit(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            String time = new Date().toString();
                            producer.send(new Message("test", time.getBytes()), new SendCallback() {
                                @Override
                                public void onSuccess(SendResult sendResult) {
                                    System.out.println("发送消息成功:" + time);
                                }

                                @Override
                                public void onException(Throwable e) {
                                    System.out.println("发送消息失败:" + time);
                                }
                            });
                            Thread.currentThread().sleep(500);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

}
