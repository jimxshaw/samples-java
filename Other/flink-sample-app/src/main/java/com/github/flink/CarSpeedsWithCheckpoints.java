package com.github.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class CarSpeedsWithCheckpoints {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        DataStream<String> averageViewStream = dataStream.map(new Speed())
                                                        .keyBy(0)
                                                        .flatMap(new AverageSpeed());

        env.execute("Car Speeds");
    }

    public static class Speed implements MapFunction<String, Tuple2<Integer, Double>> {

        @Override
        public Tuple2<Integer, Double> map(String row) throws Exception {
            try {
                return Tuple2.of(1, Double.parseDouble(row));
            }
            catch (Exception ex) {
                System.out.println(ex);
            }

            return null;
        }
    }
}
