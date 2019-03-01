//package jmeter;
//
//import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
//import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
//import org.apache.jmeter.samplers.SampleResult;
//
//import java.text.MessageFormat;
//import java.util.concurrent.atomic.AtomicInteger;
//
///**
// * Created by youzhihao on 2019/1/31.
// * jmeter运行java压测用例
// */
//public class JavaSamplerClient extends AbstractJavaSamplerClient {
//    private AtomicInteger count = new AtomicInteger();
//
//
//    @Override
//    public void setupTest(JavaSamplerContext context) {
//        count.addAndGet(1);
//        System.out.println(MessageFormat.format("count={0}", count));
//        super.setupTest(context);
//    }
//
//    @Override
//    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {
//        return new SampleResult();
//    }
//}
