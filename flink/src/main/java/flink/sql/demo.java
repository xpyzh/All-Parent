package flink.sql;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;

/**
 * @Copyright: Unicom (Zhejiang) Industrial Internet Co., Ltd.    2020 <br/>
 * @Desc: <br/>
 * @ProjectName: All-Parent <br/>
 * @Date: 2020/7/7 4:15 PM <br/>
 * @Author: youzhihao
 */
public class demo {

    public static void main(String[] args) {

    }

    /**
     * FLINK BATCH QUERY:
     * 1.TableEnvironment 初始化
     */
    public void initTableEnvironment() {
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        TableEnvironment fsTableEnv = TableEnvironment.create(fsSettings);
    }

    /**
     *  FLINK STREAMING QUERY:
     *  1.StreamTableEnvironment 初始化
     */
    public void initStreamTableEnvironment() {
        EnvironmentSettings fsSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build();
        StreamExecutionEnvironment fsEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment fsTableEnv = StreamTableEnvironment.create(fsEnv, fsSettings);
    }


}
