package com.github.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class WordCount {
    public static void main(String[] args) throws Exception {
        final ParameterTool params = ParameterTool.fromArgs(args);

        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.getConfig().setGlobalJobParameters(params);

        DataStream<String> dataStream = StreamUtil.getDataStream(env, params);

        if (dataStream == null) {
            System.exit(1);

            return;
        }

        // Convert into a keyed stream and count word frequency.
        // Takes input sentence and break it into individual words, a tuple of <word, 1>, and associate
        // a count of 1 with every word.
        // KeyBy takes in an index, in this case it's 0 which is the first parameter in the tuple
        // to use as the key.
        // The sum up the count of each word with index 1, which in this case is the integer of 1 in the tuple.
        DataStream<Tuple2<String, Integer>> wordCountStream = dataStream.flatMap(new WordCountSplitter())
                                                                        .keyBy(0)
                                                                        .sum(1);

        wordCountStream.print();

        env.execute("Word Count");
    }

    // FlatMap designates an input of String and an output of tuple of <word, 1>.
    public static class WordCountSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {

        @Override
        public void flatMap(String sentence, Collector<Tuple2<String, Integer>> out) throws Exception {
            for (String word : sentence.split(" ")) {
                out.collect(new Tuple2<>(word, 1));
            }
        }
    }
}
