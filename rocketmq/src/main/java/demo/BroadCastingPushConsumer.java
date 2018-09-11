package demo;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * Created by youzhihao on 2018/9/6.
 * 目的:主要来验证PushConsumer在BroadCasting模式下的offset
 * 结论:PushConsumer在BroadCasting模式下的offset由客户端控制，默认使用LocalFileOffsetStore类在本地写入offset文件，如果
 * 该文件删除，则重新开始读取
 */
public class BroadCastingPushConsumer {

    public static void main(String[] args) {
        initConusmer();


    }

    public static DefaultMQPushConsumer initConusmer() {
        DefaultMQPushConsumer consumer = null;
        try {
            consumer = new DefaultMQPushConsumer("BroadCastingPushConsumer2");
            consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
            consumer.setMessageModel(MessageModel.CLUSTERING);
            consumer.setNamesrvAddr("127.0.0.1:9876");
            consumer.subscribe("test", "*");
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                    for (MessageExt messageExt : msgs) {
                        try {
                            System.out.println("消费成功:" + new String(messageExt.getBody()));
                        } catch (Exception e) {
                            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                        }
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return consumer;
    }
}
