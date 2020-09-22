package flink.datastream;

import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/7/15 2:31 PM <br/>
 * @Author: youzhihao
 */
public class MinByDemo {

    public static void main(String[] args) throws Exception {
        //获取运行环境的上下文
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //获取数据源
        List data = new ArrayList();
        data.add(new Tuple3<>(0, 1, 0));
        data.add(new Tuple3<>(0, 1, 1));
        data.add(new Tuple3<>(0, 2, 2));
        data.add(new Tuple3<>(0, 1, 3));
        data.add(new Tuple3<>(1, 2, 13));
        data.add(new Tuple3<>(1, 3, 5));
        data.add(new Tuple3<>(1, 4, 9));
        data.add(new Tuple3<>(1, 6, 11));
        DataStreamSource<Tuple3<Integer, Integer, Integer>> items = env.fromCollection(data);
        items.keyBy(e -> e.f0).minBy(2).printToErr();
        //一定要触发执行，不然没结果输出
        env.execute();
    }

}
