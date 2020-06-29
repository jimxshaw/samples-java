package com.github.flink;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple3;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

public class ViewsSessionWindow {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        // We want the maximum time spent by a user on a page on our website in one session.
        // Every input entity has 3 information fields:
        // user ID, website ID that the user is viewing and the time in minutes of how long
        // the user spent on the website.
        // This is the output of the map operation with a tuple of user id, webpage id, time in minutes.
        // The window operation states the length of time of a session. Any messages that arrive
        // within that session time are part of that session.
        DataStream<Tuple3<String, String, Double>> averageViewStream = dataStream.map(new RowSplitter())
                                                                                .keyBy(0, 1)
                                                                                .window(ProcessingTimeSessionWindows.withGap(Time.seconds(10)))
                                                                                .max(2);

        averageViewStream.print();

        env.execute("Average Views per User and per User, per Page");
    }

    public static class RowSplitter implements MapFunction<String, Tuple3<String, String, Double>> {
        // Split input by comma.
        @Override
        public Tuple3<String, String, Double> map(String row) throws Exception {
            String[] fields = row.split(",");

            if (fields.length == 3) {
                return new Tuple3<>(
                        fields[0], /* user id */
                        fields[1], /* webpage id */
                        Double.parseDouble(fields[2]) /* view time in minutes */ );
            }

            return null;
        }
    }
}
