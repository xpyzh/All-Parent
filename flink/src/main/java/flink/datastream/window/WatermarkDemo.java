package flink.datastream.window;


import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.eventtime.SerializableTimestampAssigner;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/7/21 10:52 下午 <br/>
 * @Author: youzhihao
 */
public class WatermarkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        /*设置使用EventTime作为Flink的时间处理标准，不指定默认是ProcessTime*/
        senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);

        //这里为了便于理解，设置并行度为1,默认并行度是当前机器的cpu数量
        senv.setParallelism(1);
        /*指定数据源 从socket的9000端口接收数据，先进行了不合法数据的过滤*/
        DataStream<String> sourceDS = senv.socketTextStream("localhost", 9000, "\n", 2)
                .filter(new FilterFunction<String>() {
                    @Override
                    public boolean filter(String line) throws Exception {
                        if (null == line || "".equals(line)) {
                            return false;
                        }
                        String[] lines = line.split(",");
                        if (lines.length != 2) {
                            return false;
                        }
                        return true;
                    }
                });
        DataStream<Tuple2<Long, String>> wordDS = sourceDS.map(new MapFunction<String, Tuple2<Long, String>>() {
            @Override
            public Tuple2<Long, String> map(String value) throws Exception {
                String[] lines = value.split(",");
                String time=lines[0];
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return new Tuple2<Long, String>(simpleDateFormat.parse(time).getTime(), lines[1]);
            }
        });
        WatermarkStrategy<Tuple2<Long, String>> watermarkStrategy = WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(0));
        watermarkStrategy.withTimestampAssigner((SerializableTimestampAssigner<Tuple2<Long, String>>) (element, recordTimestamp) -> element.f0);
        wordDS.assignTimestampsAndWatermarks(watermarkStrategy).keyBy(value -> value.f1).window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .apply(new WindowFunction<Tuple2<Long, String>, Object, String, TimeWindow>() {
                    @Override
                    public void apply(String s, TimeWindow window, Iterable<Tuple2<Long, String>> input, Collector<Object> out) throws Exception {
                        List<Long> timeList = new ArrayList<>();
                        input.forEach(e -> timeList.add(e.f0));
                        System.out.println(MessageFormat.format("key={0},timeList={1}", s, JSONObject.toJSONString(timeList)));
                    }
                });
        senv.execute("Window WordCount");
    }

}
