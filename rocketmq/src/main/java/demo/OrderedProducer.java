package demo;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * Created by youzhihao on 2018/6/7.
 */
public class OrderedProducer {

    public static void main(String[] args) throws Exception {
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("example_group_name");
        producer.setNamesrvAddr("10.200.179.232:9876");
        //Launch the instance.ls
        producer.start();
        for (int i = 0; i < 100; i++) {
            Message msg = new Message("yxmain-helios-change-topic", "TagA", "key",("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            int orderId = i % 10;
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
