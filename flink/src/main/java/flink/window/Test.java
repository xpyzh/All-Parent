package flink.window;

import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.AssignerWithPunctuatedWatermarks;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.api.functions.windowing.AllWindowFunction;
import org.apache.flink.streaming.api.watermark.Watermark;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import javax.annotation.Nullable;

/**
 * Created by youzhihao on 2018/4/10.
 */
public class Test {

    public static void main(String[] args) throws Exception {
        testAggregate();
    }

    public static void testApplyFunction() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStreamSource<TimeModel> input = env.addSource(new SourceFunction<TimeModel>() {
            @Override
            public void run(SourceContext<TimeModel> ctx) throws Exception {
                final Long time = System.currentTimeMillis();
                int id = 1;
                while (true) {
                    ctx.collect(new TimeModel(1, id, time));
                    id++;
                    Thread.currentThread().sleep(1000);
                }
            }

            @Override
            public void cancel() {

            }
        });
        input.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<TimeModel>() {
            @Nullable
            @Override
            public Watermark checkAndGetNextWatermark(TimeModel lastElement, long extractedTimestamp) {
                return new Watermark(System.currentTimeMillis());
            }

            @Override
            public long extractTimestamp(TimeModel element, long previousElementTimestamp) {
                return element.getTime();
            }
        }).windowAll(TumblingEventTimeWindows.of(Time.seconds(10)))
                .allowedLateness(Time.seconds(10))
                .apply(new AllWindowFunction<TimeModel, Object, TimeWindow>() {
                    @Override
                    public void apply(TimeWindow window, Iterable<TimeModel> values, Collector<Object> out) throws Exception {
                        System.out.println(String.format("apply start startTime=%s,endTime=%s", window.getStart(), window.getEnd()));
                        for (TimeModel timeModel : values) {
                            System.out.println(String.format("id:%s,time:%s", timeModel.getId(), timeModel.getTime()));
                        }
                        System.out.println("apply end");
                    }
                }).addSink(new PrintSinkFunction<>());
        env.execute();
    }

    public static void testAggregate() throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        DataStreamSource<TimeModel> input = env.addSource(new SourceFunction<TimeModel>() {
            @Override
            public void run(SourceContext<TimeModel> ctx) throws Exception {
                final Long time = System.currentTimeMillis();
                int id = 1;
                while (true) {
                    ctx.collect(new TimeModel(1, id, time));
                    id++;
                    Thread.currentThread().sleep(1000);
                }
            }

            @Override
            public void cancel() {

            }
        });
        input.assignTimestampsAndWatermarks(new AssignerWithPunctuatedWatermarks<TimeModel>() {
            @Nullable
            @Override
            public Watermark checkAndGetNextWatermark(TimeModel lastElement, long extractedTimestamp) {
                return new Watermark(System.currentTimeMillis());
            }

            @Override
            public long extractTimestamp(TimeModel element, long previousElementTimestamp) {
                return element.getTime();
            }
        }).keyBy("key").window(TumblingEventTimeWindows.of(Time.seconds(10)))
                .allowedLateness(Time.seconds(10))
                .aggregate(new AggregateFunction<TimeModel, TotalModel, TotalModel>() {
                    @Override
                    public TotalModel createAccumulator() {
                        return new TotalModel();
                    }

                    @Override
                    public TotalModel add(TimeModel value, TotalModel accumulator) {
                        return new TotalModel(accumulator.getTotal() + 1);
                    }

                    @Override
                    public TotalModel getResult(TotalModel accumulator) {
                        System.out.println("getResult");
                        return accumulator;
                    }

                    @Override
                    public TotalModel merge(TotalModel a, TotalModel b) {
                        System.out.println("merge");
                        return null;
                    }
                }).addSink(new SinkFunction<TotalModel>() {
            @Override
            public void invoke(TotalModel value, Context context) throws Exception {
                System.out.println(String.format("value:%d", value.getTotal()));
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });
        env.execute();

    }
}

