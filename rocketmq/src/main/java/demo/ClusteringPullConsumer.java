package demo;

import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.text.MessageFormat;
import java.util.Set;

/**
 * Created by youzhihao on 2018/9/6.
 */
public class ClusteringPullConsumer {

    public static void main(String[] args) {
        initConusmer();

    }

    public static void initConusmer() {
        try {
            DefaultMQPullConsumer consumer = new DefaultMQPullConsumer("ClusteringPullConsumer");
            consumer.setNamesrvAddr("127.0.0.1:9876");
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.start();
            Set<MessageQueue> queueSet = consumer.fetchSubscribeMessageQueues("test");
            //Set<MessageQueue> queueSet = consumer.fetchMessageQueuesInBalance("test");
            for (MessageQueue queue : queueSet) {
                long offset = consumer.fetchConsumeOffset(queue, true);
                System.out.println(MessageFormat.format("queueId={0},offset={1}", queue.getQueueId(), offset));
                PullResult pullResult = consumer.pull(queue, null, offset, 100);
                for (MessageExt messageExt : pullResult.getMsgFoundList()) {
                    System.out.println("消费成功:" + new String(messageExt.getBody()));
                }
                consumer.updateConsumeOffset(queue, pullResult.getMaxOffset());
//                consumer.setPersistConsumerOffsetInterval();
                //异步拉取
//                consumer.pull(queue, null, offset, 100, new PullCallback() {
//                    @Override
//                    public void onSuccess(PullResult pullResult) {
//                        for (MessageExt messageExt : pullResult.getMsgFoundList()) {
//                            System.out.println("消费成功:" + new String(messageExt.getBody()));
//                        }
//                    }
//
//                    @Override
//                    public void onException(Throwable e) {
//                        e.printStackTrace();
//                    }
//                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
