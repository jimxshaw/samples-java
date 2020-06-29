package com.github.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingAlignedProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

public class WordCountCountWindow {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        // Split in the input sentence into individual words.
        // Note that the resultant stream is not keyed by an integer.
        // This is because the returned type is an object and not a
        // tuple and we want to key by one of the variables in that object.
        // Call count window and specific the number of entities in every window.
        // Sum by the count variable.
        DataStream<WordCount> wordCountStream = dataStream.flatMap(new WordCountSplitter())
                                                            .keyBy("word")
                                                            .countWindow(4)
                                                            .sum("count");

        wordCountStream.print();

        env.execute("Count Window");
    }

    public static class WordCountSplitter implements FlatMapFunction<String, WordCount> {

        @Override
        public void flatMap(String sentence, Collector<WordCount> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new WordCount(word, 1));
            }
        }
    }

    public static class WordCount {
        public String word;
        public Integer count;

        public WordCount() {

        }

        public WordCount(String word, Integer count) {
            this.word = word;
            this.count = count;
        }

        public String toString() {
            return word + ": " + count;
        }
    }
}
