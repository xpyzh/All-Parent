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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by youzhihao on 2019/3/1.
 */
public class RocketMQSender extends AbstractJavaSamplerClient {

    private static Logger log = LoggerFactory.getLogger(RocketMQSender.class);

    private int MSG_SIZE;

    private String NAMESRV_ADDRESS;

    private String TOPIC_NAME;


    private int INSTANCENUM;

    private boolean isSync;

    private static long runTimes = 1;

    private static List<DefaultMQProducer> producerList = new ArrayList<>();

    private static AtomicBoolean isInit = new AtomicBoolean(false);

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    @Override
    public void setupTest(JavaSamplerContext context) {
        MSG_SIZE = context.getIntParameter("msgSize", 512);
        NAMESRV_ADDRESS = context.getParameter("nameSrv", "10.130.69.222:9876;10.130.69.223:9876");
        TOPIC_NAME = context.getParameter("topicName", "test");
        INSTANCENUM = context.getIntParameter("instanceNum", 1);
        isSync = Boolean.valueOf(context.getParameter("isSync", "true"));
        initProducer();
    }

    private void initProducer() {
        if (isInit.compareAndSet(false, true)) {
            log.info(" [setupTest] instanceNum={},msgSize={},nameSrv={},topicName={}", INSTANCENUM, MSG_SIZE, NAMESRV_ADDRESS, TOPIC_NAME);
            for (int i = 0; i < INSTANCENUM; i++) {
                DefaultMQProducer producer = new DefaultMQProducer("producer-" + i);
                producer.setNamesrvAddr(NAMESRV_ADDRESS);
                producer.setInstanceName("instance-" + i);
                producerList.add(producer);
                try {
                    producer.start();
                } catch (MQClientException e) {
                    log.error("setupTest error", e);
                }

            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
        } else {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public SampleResult runTest(JavaSamplerContext context) {
        SampleResult sampleResult = new SampleResult();
        log.info("runTimes={},producerList={}", runTimes, producerList.size());
        int index = (int) (runTimes % producerList.size());
        log.info("index={}", index);
        DefaultMQProducer producer = producerList.get(index);
        try {
            if (runTimes++ % 1000 == 0) {
                log.info("[runTest] instanceName={},instanceNum={},msgSize={},nameSrv={},topicName={}", producer.getInstanceName(), INSTANCENUM, MSG_SIZE, NAMESRV_ADDRESS, TOPIC_NAME);
            }
            sampleResult.sampleStart();
            Message message = new Message(TOPIC_NAME, new byte[MSG_SIZE]);
            if (isSync) {
                SendResult sendResult = producer.send(message);
                if (sendResult.getSendStatus() != SendStatus.SEND_OK) {
                    log.warn("producer send status={}", sendResult.getSendStatus().toString());
                }
                sampleResult.setSuccessful(true);
            } else {
                producer.sendOneway(message);
                sampleResult.setSuccessful(true);
//                producer.send(message, new SendCallback() {
//                    @Override
//                    public void onSuccess(SendResult sendResult) {
//                        sampleResult.setResponseOK();
//                    }
//
//                    @Override
//                    public void onException(Throwable e) {
//                        log.error("producer send error", e);
//                        sampleResult.setSuccessful(false);
//                    }
//                });
            }
            sampleResult.sampleEnd();
        } catch (Throwable e) {
            log.error("producer send error", e);
            sampleResult.setSuccessful(false);
        }
        return sampleResult;
    }

    @Override
    public void teardownTest(JavaSamplerContext context) {

    }

    @Override
    public Arguments getDefaultParameters() {
        Arguments params = new Arguments();
        params.addArgument("msgSize", "512");
        params.addArgument("nameSrv", "10.130.69.222:9876;10.130.69.223:9876");
        params.addArgument("topicName", "test");
        params.addArgument("instanceNum", "1");
        params.addArgument("isSync", "true");
        return params;
    }
}
