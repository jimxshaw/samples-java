package com.github.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class AverageViews {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        // Map splits the row into <webpageid, view in minutes, 1>.
        // The key is the web page id.
        // Track the running sum and count for each time the page
        // was viewed <webpageid, time, count>.
        // Find the average number of minutes spend on each web page.
        DataStream<Tuple2<String, Double>> averageViewStream = dataStream.map(new RowSplitter())
                                                                        .keyBy(0)
                                                                        .reduce(new SumAndCount())
                                                                        .map(new Average());

        averageViewStream.print();

        env.execute("Average Views");
    }

    // Input string and return a tuple of <webpageid, view in minutes, 1>.
    public static class RowSplitter implements MapFunction<String, Tuple3<String, Double, Integer>> {

        @Override
        public Tuple3<String, Double, Integer> map(String row) throws Exception {
            String[] fields = row.split(",");

            if (fields.length == 2) {
                return new Tuple3<>(fields[0], Double.parseDouble(fields[1]), 1);
            }

            return null;
        }
    }

    public static class SumAndCount implements ReduceFunction<Tuple3<String, Double, Integer>> {

        @Override
        public Tuple3<String, Double, Integer> reduce(Tuple3<String, Double, Integer> cumulative,
                                                      Tuple3<String, Double, Integer> input) throws Exception {
            // Add up the cumulative results of the reduce method tracked by Flink and the current input.
            // In the tuple, the first value is the key itself. Second and third values are summing
            // of the cumulative values so far and the new input, new streaming entity.
            // F1 is the number of minutes a particular user stayed on a particular web page and
            // F2 is a count of users who visited that page.
            return new Tuple3<>(input.f0, cumulative.f1 + input.f1, cumulative.f2 + input.f2);
        }
    }

    public static class Average implements MapFunction<Tuple3<String, Double, Integer>, Tuple2<String, Double>> {

        @Override
        public Tuple2<String, Double> map(Tuple3<String, Double, Integer> input) throws Exception {
            // Average view time is the sum / count.
            return new Tuple2<>(input.f0, input.f1 / input.f2);
        }
    }
}
