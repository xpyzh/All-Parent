package flink.datastream;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/7/15 3:07 PM <br/>
 * @Author: youzhihao
 */
public class ReduceDemo {

    public static void main(String[] args) throws Exception {
        //获取运行环境的上下文
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //获取数据源
        List data = new ArrayList();
        data.add(new Tuple2<>(0, 1));
        data.add(new Tuple2<>(0, 2));
        data.add(new Tuple2<>(0, 3));
        data.add(new Tuple2<>(0, 4));
        data.add(new Tuple2<>(1, 5));
        data.add(new Tuple2<>(1, 7));
        data.add(new Tuple2<>(1, 9));
        data.add(new Tuple2<>(1, 11));
        DataStreamSource<Tuple2<Integer, Integer>> items = env.fromCollection(data);
        items.keyBy(e -> e.f0).reduce((value1, value2) -> {
            return new Tuple2(value1.f0, value2.f1 - value1.f1);

        }).printToErr();
        env.execute();
    }

}
