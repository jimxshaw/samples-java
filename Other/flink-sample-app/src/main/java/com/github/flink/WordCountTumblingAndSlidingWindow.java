package com.github.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingAlignedProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCountTumblingAndSlidingWindow {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        // Read in the input sentence, split it up into individual words.
        // Take the count of 1 with every word.
        // Create a keyed stream with the word as the key, as
        // shown by the 0 index in the keyBy method.
        // Apply a window operation to the values
        // associated with every key.
        // Sum all the entities in that window.
        DataStream<Tuple2<String, Integer>> wordCountStream = dataStream.flatMap(new WordCountSplitter())
                                                                        .keyBy(0)
                                                                        .window(TumblingAlignedProcessingTimeWindows.of(Time.seconds(10)))
                                                                        .sum(1);

        env.execute("Tumbling and Sliding Window");
    }

    public static class WordCountSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        // In a flatmap, a single entity in the input stream can map to one or more
        // entities in the resultant stream.
        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        }
    }
}
