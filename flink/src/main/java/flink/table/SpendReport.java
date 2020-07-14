/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package flink.table;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Tumble;
import org.apache.flink.table.api.bridge.java.BatchTableEnvironment;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.walkthrough.common.table.BoundedTransactionTableSource;
import org.apache.flink.walkthrough.common.table.SpendReportTableSink;
import org.apache.flink.walkthrough.common.table.TruncateDateToHour;
import org.apache.flink.walkthrough.common.table.UnboundedTransactionTableSource;

/**
 * table api 官方demo
 * 这里的demo对应的api版本比较老，后面会用新的api重写一版
 */
public class SpendReport {

    public static void main(String[] args) throws Exception {
        demo();
        //boundedDemo1();
        //boundedDemo2();
        //unboundedDemo1();
    }

    //有界的demo，基础数据遍历输出
    public static void demo() throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);

        tEnv.registerTableSource("transactions", new BoundedTransactionTableSource());
        tEnv.registerTableSink("spend_report", new SpendReportTableSink());
        tEnv.registerFunction("truncateDateToHour", new TruncateDateToHour());
        tEnv.scan("transactions")
                .orderBy("accountId.asc")
                .insertInto("spend_report");
        env.execute("Spend Report");
    }

    //有界的demo，使用自定义函数
    public static void boundedDemo1() throws Exception {
        //初始化有界的环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);
        //定义source，这里为一个有界数据源
        tEnv.registerTableSource("transactions", new BoundedTransactionTableSource());
        //定义sink，这里的实现只是简单打一个log
        tEnv.registerTableSink("spend_report", new SpendReportTableSink());
        //定义一个自定义函数，用来将时间戳进行小时取整
        tEnv.registerFunction("truncateDateToHour", new TruncateDateToHour());
        tEnv.scan("transactions")
                // 查询需要的字段，并且使用自定义函数truncateDateToHour，将时间处理成整点时间戳
                .select("accountId, timestamp.truncateDateToHour as timestamp, amount")
                // 根据accountId和timestamp进行分组
                .groupBy("accountId, timestamp")
                .select("accountId, timestamp, amount.sum as total")
                // 输出到SpendReportTableSink中
                .insertInto("spend_report");
        env.execute("Spend Report");
    }

    //有界的demo，使用window
    public static void boundedDemo2() throws Exception {
        //初始化有界的环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tEnv = BatchTableEnvironment.create(env);
        //定义source，这里为一个有界数据源
        tEnv.registerTableSource("transactions", new BoundedTransactionTableSource());
        //定义sink，这里的实现只是简单打一个log
        tEnv.registerTableSink("spend_report", new SpendReportTableSink());
        tEnv.scan("transactions")
                //定义时间窗口进行数据聚合，这里1个小时为一个窗口，取timestamp字段进行分组
                .window(Tumble.over("1.hour").on("timestamp").as("w"))
                // 根据accountId和w进行分组
                .groupBy("accountId, w")
                .select("accountId, w.start as timestamp, amount.sum as total")
                // 输出到SpendReportTableSink中
                .insertInto("spend_report");
        env.execute("Spend Report");
    }

    //无界数据流demo，使用window
    public static void unboundedDemo1()throws Exception {
        //初始化无界环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //定义时间语义
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);
        //定义source，这里为一个无界数据源
        tEnv.registerTableSource("transactions", new UnboundedTransactionTableSource());
        //定义sink，这里的实现只是简单打一个log
        tEnv.registerTableSink("spend_report", new SpendReportTableSink());
        tEnv.scan("transactions")
                //定义时间窗口进行数据聚合，这里1个小时为一个窗口，取timestamp字段进行分组
                .window(Tumble.over("1.hour").on("timestamp").as("w"))
                // 根据accountId和w进行分组
                .groupBy("accountId, w")
                .select("accountId, w.start as timestamp, amount.sum as total")
                // 输出到SpendReportTableSink中
                .insertInto("spend_report");
        env.execute("Spend Report");
    }
}
