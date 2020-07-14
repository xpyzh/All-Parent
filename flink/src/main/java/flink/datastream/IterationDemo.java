package flink.datastream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.IterativeStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/7/13 9:10 PM <br/>
 * @Author: youzhihao
 */
public class IterationDemo {

    private static final Logger logger = LoggerFactory.getLogger(IterationDemo.class);

    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<Long> someIntegers = env.generateSequence(0, 5);
        IterativeStream<Long> iteration = someIntegers.iterate();
        DataStream<Long> minusOne = iteration.map(new MapFunction<Long, Long>() {
            @Override
            public Long map(Long value) throws Exception {
                logger.info("value:" + value);
                return value;
            }
        });
        DataStream<Long> stillGreaterThanZero = minusOne.filter(new FilterFunction<Long>() {
            @Override
            public boolean filter(Long value) throws Exception {
                return (value > 0);
            }
        });
        // 因为流式计算是没有边界的，因此对应的IterativeStream也不应该有边界，
        // closeWith定义后续的流，使得整个迭代流形成一个闭环
        iteration.closeWith(stillGreaterThanZero);
        env.execute();
    }

}
