package jmeter;

import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by youzhihao on 2019/3/1.
 */
public class RocketMQSender extends AbstractJavaSamplerClient {

    private static Logger log = LoggerFactory.getLogger(RocketMQSender.class);

    private int MSG_SIZE;

    private int THREAD_NUM;

    private String NAMESRV_ADDRESS;

    private String TOPIC_NAME;

    private DefaultMQProducer producer;

    private ExecutorService executor;

    private static AtomicInteger processCount = new AtomicInteger(0);


    @Override
    public void setupTest(JavaSamplerContext context) {
        int index = processCount.addAndGet(1);
        MSG_SIZE = context.getIntParameter("msgSize", 512);
        THREAD_NUM = context.getIntParameter("threadNum", 5);
        NAMESRV_ADDRESS = context.getParameter("nameSrv", "10.130.69.222:9876;10.130.69.223:9876");
        TOPIC_NAME = context.getParameter("topicName", "test");
        executor = Executors.newFixedThreadPool(THREAD_NUM);
        producer = new DefaultMQProducer("producer-" + index);
        producer.setNamesrvAddr(NAMESRV_ADDRESS);
        producer.setInstanceName("instance-" + index);
        try {
            producer.start();
        } catch (MQClientException e) {
            log.error("setupTest error", e);
        }
        log.info("index={},msgSize={},threadNum={},nameSrv={},topicName={}", index, MSG_SIZE, THREAD_NUM, NAMESRV_ADDRESS, TOPIC_NAME);
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sampleResult = new SampleResult();
        sampleResult.sampleStart();
        Future<Boolean> result = executor.submit(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                Message message = new Message(TOPIC_NAME, new byte[MSG_SIZE]);
                try {
                    SendResult sendResult = producer.send(message);
                    if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
                        log.warn("producer send status={}", sendResult.getSendStatus().toString());
                    }
                } catch (Exception e) {
                    log.error("producer send error", e);
                }
                return true;
            }
        });
        try {
            result.get();
            sampleResult.sampleEnd();
            sampleResult.setSuccessful(true);
        } catch (Exception e) {
            log.error("producer send error", e);
            sampleResult.setSuccessful(false);
        }
        return sampleResult;
    }

    @Override
    public Arguments getDefaultParameters() {
        return super.getDefaultParameters();
    }
}
